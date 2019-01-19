package com.example.haris.finalproject.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.activity.INTENT_DETAIL_REQUEST_CODE
import com.example.haris.finalproject.activity.MatchDetailActivity
import com.example.haris.finalproject.Interface.PrevMatchView
import com.example.haris.finalproject.Presenter.LastMatchPresenter
import com.example.haris.finalproject.adapter.MatchRecyclerViewAdapter
import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.model.LeagueItem
import com.example.haris.finalproject.model.LeagueResponse

import com.example.haris.finalproject.R
import com.example.haris.finalproject.utils.gone
import com.example.haris.finalproject.utils.invisible
import com.example.haris.finalproject.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*


class LastMatchFragment : Fragment(),PrevMatchView, SearchView.OnQueryTextListener {

    private  var events : MutableList<EventItem> = mutableListOf()
    private  var originEvents : MutableList<EventItem> = mutableListOf()
    private lateinit var league: LeagueItem
    private lateinit var presenter: LastMatchPresenter
    private val matchAdapter =
        MatchRecyclerViewAdapter(events) { item: EventItem ->
            itemClicked(item)
        }
    val gson = Gson()
    val apiRepository = ApiRespository()
    private lateinit var searchView: SearchView


    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String): Boolean {
        var searchInput = p0.toLowerCase()
        var resultData = originEvents.filter { it.strHomeTeam!!.toLowerCase().contains(searchInput) || it.strAwayTeam!!.toLowerCase().contains(searchInput) }
        events.clear()
        events.addAll(resultData)
        matchAdapter.notifyDataSetChanged()
        return true

    }



    override fun showLoading() {
        last_match_progress_bar.visible()
        rv_last_match.invisible()
        sp_last_match.invisible()

    }

    override fun hideLoading() {
        last_match_progress_bar.gone()
        sp_last_match.visible()
        rv_last_match.visible()


    }

    private fun itemClicked(item: EventItem) {
        val intent = Intent(activity, MatchDetailActivity::class.java)
        intent.putExtra(INTENT_DETAIL_REQUEST_CODE, item)
       // intent.putExtra("isNextMatch",false)
        startActivity(intent)
       // startActivity<MatchDetailActivity>(INTENT_DETAIL_REQUEST_CODE to item)
        // Toast.makeText(activity, "Its toast!", Toast.LENGTH_SHORT).show()
    }
    override fun showLeagueList(data: LeagueResponse) {
        sp_last_match.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,data.leagues)
        sp_last_match.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                league = sp_last_match.selectedItem as LeagueItem
                presenter.getPastEvent(league.idLeague!!)
                searchView.setQuery("", false)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

    }

    override fun showEventListPrev(data: List<EventItem>) {
        events.clear()
        events.addAll(data)
        originEvents.clear()
        originEvents.addAll(data)
        matchAdapter.notifyDataSetChanged()
        rv_last_match.scrollToPosition(0)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_last_match, container, false)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)
       // tv_toolbar_title.text = "Last Match"
        presenter = LastMatchPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getLeagueAll()
        rv_last_match.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_last_match.adapter = matchAdapter

    }
    override fun onResume() {
        //(activity as AppCompatActivity).supportActionBar?.title = "Last Match"
        super.onResume()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.manu_bar, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_last_match)
        searchView.setOnQueryTextListener(this)
    }

}
