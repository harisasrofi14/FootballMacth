package com.example.haris.finalproject.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.activity.INTENT_DETAIL_REQUEST_CODE
import com.example.haris.finalproject.adapter.MatchRecyclerViewAdapter
import com.example.haris.finalproject.Interface.NextMatchView
import com.example.haris.finalproject.activity.MatchDetailActivity
import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.model.LeagueItem
import com.example.haris.finalproject.model.LeagueResponse
import com.example.haris.finalproject.Presenter.NextMatchPresenter
import com.example.haris.finalproject.R
import com.example.haris.finalproject.utils.gone
import com.example.haris.finalproject.utils.invisible
import com.example.haris.finalproject.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*
import android.view.MenuInflater




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment(),NextMatchView, SearchView.OnQueryTextListener {


    private  var events : MutableList<EventItem> = mutableListOf()
    private  var originEvents : MutableList<EventItem> = mutableListOf()
    private lateinit var league: LeagueItem
    private lateinit var searchView: SearchView
    private lateinit var presenter: NextMatchPresenter
    private val matchAdapter =
        MatchRecyclerViewAdapter(events) { item: EventItem ->
            itemClicked(item)
        }
    val gson = Gson()
    val apiRepository = ApiRespository()



    fun NextMatchFragment() {
        // Required empty public constructor
    }

    override fun showLoading() {
        next_match_progress_bar.visible()
        sp_next_match.invisible()
        rv_next_match.invisible()
    }

    override fun hideLoading() {
        next_match_progress_bar.gone()
        sp_next_match.visible()
        rv_next_match.visible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        sp_next_match.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, data.leagues)
        sp_next_match.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                league = sp_next_match.selectedItem as LeagueItem
                presenter.getNextEvent(league.idLeague!!)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

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

    override fun showEventListNext(data: List<EventItem>) {
        events.clear()
        events.addAll(data)
        originEvents.clear()
        originEvents.addAll(data)
        matchAdapter.notifyDataSetChanged()
        rv_next_match.scrollToPosition(0)
    }

    private fun itemClicked(item: EventItem) {
        val intent = Intent(activity, MatchDetailActivity::class.java)
        intent.putExtra(INTENT_DETAIL_REQUEST_CODE, item)
       // intent.putExtra("isNextMatch",true)
        startActivity(intent)
        //startActivity<MatchDetailActivity>(INTENT_DETAIL_REQUEST_CODE to item)
        // Toast.makeText(activity, "Its toast!", Toast.LENGTH_SHORT).show()
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
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.manu_bar, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_next_match)
        searchView.setOnQueryTextListener(this)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)

        presenter = NextMatchPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getLeagueAll()
        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_next_match.adapter = matchAdapter
    }


}
