package com.example.haris.finalproject.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.activity.*
import com.example.haris.finalproject.adapter.PlayerRecyclerViewAdapter
import com.example.haris.finalproject.Interface.TeamPlayerView
import com.example.haris.finalproject.model.PlayerItem
import com.example.haris.finalproject.Presenter.TeamPlayerPresenter

import com.example.haris.finalproject.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.support.v4.startActivity

class PlayerFragment : Fragment(),TeamPlayerView {

    private  var players : MutableList<PlayerItem> = mutableListOf()

    private lateinit var presenter: TeamPlayerPresenter
    private val playerAdapter =
        PlayerRecyclerViewAdapter(players) { item: PlayerItem ->
            itemClicked(item)
        }
    val gson = Gson()
    val apiRepository = ApiRespository()
    private var idTeam = "0"




    private fun itemClicked(item: PlayerItem) {
        startActivity<PlayerDetailActivity>(INTENT_PLAYER_DETAIL_REQUEST_CODE to item)
    }
    override fun showTeamPlayer(player: List<PlayerItem>) {
        players.clear()
        players.addAll(player)
        playerAdapter.notifyDataSetChanged()
        rv_player.scrollToPosition(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity!!.title = "Player"
        return inflater.inflate(R.layout.fragment_player, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)
        val activity = activity as TeamDetailActivity?
        if (activity != null) {
            idTeam = activity.passData()
        }
        presenter = TeamPlayerPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getPlayer(idTeam)
        rv_player.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_player.adapter = playerAdapter
    }

}
