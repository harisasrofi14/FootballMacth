package com.example.haris.finalproject.activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.helper.database
import com.example.haris.finalproject.Interface.MatchDetailView
import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.model.TeamItem
import com.example.haris.finalproject.Presenter.MatchDetailPresenter
import com.example.haris.finalproject.R
import com.example.haris.finalproject.R.drawable.ic_add_to_favorites
import com.example.haris.finalproject.R.drawable.ic_added_to_favorites
import com.example.haris.finalproject.R.id.add_to_calendar
import com.example.haris.finalproject.R.id.add_to_favorite
import com.example.haris.finalproject.R.menu.match_detail_menu
import com.example.haris.finalproject.utils.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

import java.util.*




const val INTENT_DETAIL_REQUEST_CODE = "1"
class MatchDetailActivity : AppCompatActivity(),MatchDetailView {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var isNextevent: Boolean = false
    private lateinit var eventitem: EventItem
    val gson = Gson()
    val apiRepository = ApiRespository()
    lateinit var presenter: MatchDetailPresenter

    override fun showLoading() {
        detail_progress_bar.visible()
        wrapperTeam.invisible()
        wrapper_match_detail.invisible()
    }

    override fun hideLoading() {
        detail_progress_bar.gone()
        wrapperTeam.visible()
        wrapper_match_detail.visible()
    }

    override fun showTeamDetails(dataHomeTeam: List<TeamItem>, dataAwayTeam: List<TeamItem>) {
        Picasso.get()
            .load(dataHomeTeam[0].strTeamBadge)
            .into(iv_homeTeam)

        Picasso.get()
            .load(dataAwayTeam[0].strTeamBadge)
            .into(iv_awayTeam)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        eventitem = intent.getParcelableExtra(INTENT_DETAIL_REQUEST_CODE)
        presenter = MatchDetailPresenter(this,apiRepository,gson)
        presenter.getTeamDetails(eventitem.idHomeTeam!!, eventitem.idAwayTeam!!)
        setupLayout(eventitem)
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = "Detail"
        favoriteState()
        var strDate: Date? = dateFormat(eventitem.dateEvent.toString(),eventitem.strTime.toString())
        isNextevent = Date().before(strDate)

    }

    private fun setupLayout(item: EventItem) {
        tv_homeTeam.text = item.strHomeTeam
        tv_matchDate.text = item.dateEvent
        tv_homeScore.text = item.intHomeScore
        tv_homeGoals.text = item.strHomeGoalDetails
        tv_homeShots.text = item.intHomeShots
        tv_homeGoalKeeper.text = item.strHomeLineupGoalkeeper
        tv_homeDefense.text = item.strHomeLineupDefense
        tv_homeMidfield.text = item.strHomeLineupMidfield
        tv_homeForward.text = item.strHomeLineupForward
        tv_homeSubstitutes.text = item.strHomeLineupSubstitutes
        tv_awayTeam.text = item.strAwayTeam
        tv_awayScore.text = item.intAwayScore
        tv_awayGoals.text = item.strAwayGoalDetails
        tv_awayShots.text = item.intAwayShots
        tv_awayGoalKeeper.text = item.strAwayLineupGoalkeeper
        tv_awayDefense.text = item.strAwayLineupDefense
        tv_awayMidfield.text = item.strAwayLineupMidfield
        tv_awayForward.text = item.strAwayLineupForward
        tv_awaySubstitutes.text = item.strAwayLineupSubstitutes

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(match_detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite()
                else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            add_to_calendar ->{
                if (!isNextevent)
                    Toast.makeText(this, resources.getString(R.string.match_over), Toast.LENGTH_SHORT).show()
                else
                    addToCalendar(eventitem)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToCalendar(match: EventItem) {
        val matchTitle = "${match.strHomeTeam} VS ${match.strAwayTeam}"
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, matchTitle)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, toMillis(match.dateEvent.toString(), match.strTime.toString()))
            .putExtra(CalendarContract.Events.ALL_DAY, false)
        startActivity(intent)
    }

    private fun favoriteState(){
        database.use {
            val result = select(EventItem.TABLE_FAVORITES)
                .whereArgs(EventItem.ID_EVENT +"={id}",
                    "id" to eventitem.idEvent.toString())
                .parseList(classParser<EventItem>())
            isFavorite = !result.isEmpty()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(EventItem.TABLE_FAVORITES,
                    EventItem.ID_EVENT to eventitem.idEvent,
                    EventItem.DATE to eventitem.dateEvent,
                    EventItem.TIME to eventitem.strTime,
                    // home team
                    EventItem.HOME_ID to eventitem.idHomeTeam,
                    EventItem.HOME_TEAM to eventitem.strHomeTeam,
                    EventItem.HOME_SCORE to eventitem.intHomeScore,
                    EventItem.HOME_FORMATION to eventitem.strHomeFormation,
                    EventItem.HOME_GOAL_DETAILS to eventitem.strHomeGoalDetails,
                    EventItem.HOME_SHOTS to eventitem.intHomeShots,
                    EventItem.HOME_LINEUP_GOALKEEPER to eventitem.strHomeLineupGoalkeeper,
                    EventItem.HOME_LINEUP_DEFENSE to eventitem.strHomeLineupDefense,
                    EventItem.HOME_LINEUP_MIDFIELD to eventitem.strHomeLineupMidfield,
                    EventItem.HOME_LINEUP_FORWARD to eventitem.strHomeLineupForward,
                    EventItem.HOME_LINEUP_SUBSTITUTES to eventitem.strHomeLineupSubstitutes,

                    // away team
                    EventItem.AWAY_ID to eventitem.idAwayTeam,
                    EventItem.AWAY_TEAM to eventitem.strAwayTeam,
                    EventItem.AWAY_SCORE to eventitem.intAwayScore,
                    EventItem.AWAY_FORMATION to eventitem.strAwayFormation,
                    EventItem.AWAY_GOAL_DETAILS to eventitem.strAwayGoalDetails,
                    EventItem.AWAY_SHOTS to eventitem.intAwayShots,
                    EventItem.AWAY_LINEUP_GOALKEEPER to eventitem.strAwayLineupGoalkeeper,
                    EventItem.AWAY_LINEUP_DEFENSE to eventitem.strAwayLineupDefense,
                    EventItem.AWAY_LINEUP_MIDFIELD to eventitem.strAwayLineupMidfield,
                    EventItem.AWAY_LINEUP_FORWARD to eventitem.strAwayLineupForward,
                    EventItem.AWAY_LINEUP_SUBSTITUTES to eventitem.strAwayLineupSubstitutes)
            }
        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(EventItem.TABLE_FAVORITES, EventItem.ID_EVENT+"={id}",
                    "id" to eventitem.idEvent.toString())
            }

        } catch (e: SQLiteConstraintException){

        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
