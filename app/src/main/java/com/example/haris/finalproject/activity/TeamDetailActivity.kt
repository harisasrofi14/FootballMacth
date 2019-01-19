package com.example.haris.finalproject.activity

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.example.haris.finalproject.adapter.*
import com.example.haris.finalproject.helper.database
import com.example.haris.finalproject.Interface.TeamDetailView
import com.example.haris.finalproject.model.TeamItem
import com.example.haris.finalproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

const val INTENT_TEAM_DETAIL_REQUEST_CODE = "2"
class TeamDetailActivity : AppCompatActivity(),TeamDetailView {


    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false


  /*  override fun showTeamDetails(team: List<TeamItem>) {

        Picasso.get()
            .load(team[0].strTeamBadge)
            .into(iv_team_badge)
        tv_teamName.text = team[0].strTeam
        tv_team_desc.text = team[0].strDescriptionEN
        presenter.getPlayer(item.idTeam!!)
    }*/

  /*  override fun showTeamPlayer(player: List<PlayerItem>) {
        players.clear()
        players.addAll(player)
        playerAdapter.notifyDataSetChanged()
        rv_player.scrollToPosition(0)
    } */

    /* private fun itemClicked(item: PlayerItem) {
       // startActivity<MatchDetailActivity>(INTENT_DETAIL_REQUEST_CODE to item)
        // Toast.makeText(activity, "Its toast!", Toast.LENGTH_SHORT).show()
    } */

    private lateinit var item: TeamItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        teamviewpager.adapter = TeamAdapter(supportFragmentManager)
        tabs_main_team.setupWithViewPager(teamviewpager)
        item = intent.getParcelableExtra(INTENT_TEAM_DETAIL_REQUEST_CODE)
        Picasso.get()
            .load(item.strTeamBadge)
            .into(iv_team_badge)
        tv_teamName.text = item.strTeam
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = "Team Detail"
        favoriteState()
    }
    override fun passData(): String {
        return item.idTeam!!
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite()
                else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    TeamItem.TABLE_FAVORITES,
                    TeamItem.ID_TEAM to item.idTeam,
                    TeamItem.TEAM_BADGE to item.strTeamBadge,
                    TeamItem.TEAM to item.strTeam
                )

            }
        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(TeamItem.TABLE_FAVORITES, TeamItem.ID_TEAM+"={id}",
                    "id" to item.idTeam.toString())
            }

        } catch (e: SQLiteConstraintException){

        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(TeamItem.TABLE_FAVORITES)
                .whereArgs(TeamItem.ID_TEAM +"={id}",
                    "id" to item.idTeam.toString())
                .parseList(classParser<TeamItem>())
            isFavorite = !result.isEmpty()
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}


