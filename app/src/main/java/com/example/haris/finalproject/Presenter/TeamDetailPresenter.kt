package com.example.haris.finalproject.Presenter

import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.API.TheSportBDApi
import com.example.haris.finalproject.Interface.TeamDescView
import com.example.haris.finalproject.model.TeamDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(val view: TeamDescView, private val apiRespository: ApiRespository, private val gson: Gson

) {
    fun getTeamDetails(idTeam: String) {

        view.showLoading()


        GlobalScope.launch(Dispatchers.Main) {
            val dataTeam =
                gson.fromJson(
                    apiRespository
                        .doRequest(TheSportBDApi.getTeamDetails(idTeam)).await(),
                    TeamDetailResponse::class.java
                )





            try {
                view.showTeamDetails(dataTeam.teams!!)
                view.hideLoading()
            } catch (e: NullPointerException) {

            }


        }
    }

}