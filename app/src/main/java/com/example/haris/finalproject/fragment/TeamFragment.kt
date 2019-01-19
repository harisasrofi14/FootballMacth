package com.example.haris.finalproject.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.activity.INTENT_TEAM_DETAIL_REQUEST_CODE
import com.example.haris.finalproject.activity.TeamDetailActivity
import com.example.haris.finalproject.adapter.TeamRecyclerViewAdapter
import com.example.haris.finalproject.Interface.TeamView
import com.example.haris.finalproject.model.LeagueItem
import com.example.haris.finalproject.model.LeagueResponse
import com.example.haris.finalproject.model.TeamItem
import com.example.haris.finalproject.Presenter.TeamPresenter

import com.example.haris.finalproject.R
import com.example.haris.finalproject.utils.gone
import com.example.haris.finalproject.utils.invisible
import com.example.haris.finalproject.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.startActivity


class TeamFragment : Fragment(),TeamView,SearchView.OnQueryTextListener  {


    private  var teams : MutableList<TeamItem> = mutableListOf()
    private  var originTeams : MutableList<TeamItem> = mutableListOf()
    private lateinit var league: LeagueItem
    private lateinit var searchView: SearchView
    private lateinit var presenter: TeamPresenter
    private val teamAdapter =
        TeamRecyclerViewAdapter(teams) { item: TeamItem ->
            itemClicked(item)
        }
    val gson = Gson()
    val apiRepository = ApiRespository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun showLoading() {
        team_progress_bar.visible()
        sp_team.invisible()
        rv_team.invisible()
    }

    override fun hideLoading() {
        team_progress_bar.gone()
        sp_team.visible()
        rv_team.visible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        sp_team.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,data.leagues)
        sp_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                league = sp_team.selectedItem as LeagueItem
                var a = league.strLeague
                if (a != null) {
                    a = a.replace(" ", "%20")
                }

                presenter.getTeam(a!!)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }


    override fun showTeamList(data: List<TeamItem>) {
        teams.clear()
        teams.addAll(data)
        originTeams.clear()
        originTeams.addAll(data)
        teamAdapter.notifyDataSetChanged()
        rv_team.scrollToPosition(0)
    }

    private fun itemClicked(item: TeamItem) {
        startActivity<TeamDetailActivity>(INTENT_TEAM_DETAIL_REQUEST_CODE to item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)

        presenter = TeamPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getLeagueAll()
        rv_team.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_team.adapter = teamAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.manu_bar, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_team)
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
       return false
    }

    override fun onQueryTextChange(p0: String): Boolean {
        var searchInput = p0.toLowerCase()
        var resultData = originTeams.filter { it.strTeam!!.toLowerCase().contains(searchInput) }
        teams.clear()
        teams.addAll(resultData)
        teamAdapter.notifyDataSetChanged()
        return true
    }

}
