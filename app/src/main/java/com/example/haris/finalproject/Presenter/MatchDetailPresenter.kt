package com.example.haris.finalproject.Presenter

import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.API.TheSportBDApi
import com.example.haris.finalproject.Interface.MatchDetailView
import com.example.haris.finalproject.model.TeamDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(val view: MatchDetailView, private val apiRespository: ApiRespository, private val gson: Gson
) {
    fun getTeamDetails(idHomeTeam: String, idAwayTeam: String) {
        view.showLoading()


        GlobalScope.launch(Dispatchers.Main){
            val dataHomeTeam = gson.fromJson(
                    apiRespository
                        .doRequest(TheSportBDApi.getTeamDetails(idHomeTeam)).await(),
                    TeamDetailResponse::class.java
                )


            val dataAwayTeam = gson.fromJson(
                    apiRespository
                        .doRequest(TheSportBDApi.getTeamDetails(idAwayTeam)).await(),
                    TeamDetailResponse::class.java
                )


            try {
                view.showTeamDetails(dataHomeTeam.teams!!, dataAwayTeam.teams!!)
                view.hideLoading()
            } catch (e: NullPointerException) {

            }

        }
    }
}