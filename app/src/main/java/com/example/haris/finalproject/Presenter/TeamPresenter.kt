package com.example.haris.finalproject.Presenter

import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.API.TheSportBDApi

import com.example.haris.finalproject.Interface.TeamView
import com.example.haris.finalproject.model.LeagueResponse
import com.example.haris.finalproject.model.TeamDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(private val view: TeamView, private val apiRespository: ApiRespository,
                         private val gson: Gson
) {
    fun getLeagueAll() {


        GlobalScope.launch(Dispatchers.Main) {
            val data =
                gson.fromJson(
                    apiRespository
                        .doRequest(TheSportBDApi.getLeagueAll()).await(),
                    LeagueResponse::class.java
                )


            view.showLeagueList(data)
        }
    }

        fun getTeam(id: String) {
            view.showLoading()

            GlobalScope.launch(Dispatchers.Main) {
                val data =
                    gson.fromJson(
                        apiRespository
                            .doRequest(TheSportBDApi.getTeam(id)).await(),
                        TeamDetailResponse::class.java
                    )





                try {
                    view.showTeamList(data.teams!!)
                    view.hideLoading()
                } catch (e: NullPointerException) {

                }
            }
        }
    }

