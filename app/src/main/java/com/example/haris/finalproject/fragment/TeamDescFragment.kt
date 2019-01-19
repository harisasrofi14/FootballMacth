package com.example.haris.finalproject.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.activity.TeamDetailActivity
import com.example.haris.finalproject.Interface.TeamDescView
import com.example.haris.finalproject.model.TeamItem
import com.example.haris.finalproject.Presenter.TeamDetailPresenter

import com.example.haris.finalproject.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_desc.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamDescFragment : Fragment(),TeamDescView{


    private  var teams : MutableList<TeamItem> = mutableListOf()
    private lateinit var item: TeamItem
    val gson = Gson()
    val apiRepository = ApiRespository()
    lateinit var presenter: TeamDetailPresenter
    private var idTeam = "0"


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showTeamDetails(team: List<TeamItem>) {
        tv_team_desc.text = team[0].strDescriptionEN
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_desc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)
        val activity = activity as TeamDetailActivity?
        if (activity != null) {
            idTeam = activity.passData()
        }
        presenter = TeamDetailPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getTeamDetails(idTeam)


    }
}
