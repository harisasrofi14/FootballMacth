package com.example.haris.finalproject.Presenter

import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.API.TheSportBDApi
import com.example.haris.finalproject.Interface.TeamPlayerView
import com.example.haris.finalproject.model.PlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPlayerPresenter(val view: TeamPlayerView, private val apiRespository: ApiRespository, private val gson: Gson
){
   /* fun getPlayer(idTeam: String) {





        async(context.main) {
            val dataPlayer = bg {
                gson.fromJson(
                    apiRespository
                        .doRequest(TheSportBDApi.getPlayer(idTeam)),
                    PlayerResponse::class.java
                )
            }


            try {

                    view.showTeamPlayer(dataPlayer.await().players!!)

            } catch (e: NullPointerException) {

            }


        }
    } */
    fun getPlayer(league: String) {

       GlobalScope.launch(Dispatchers.Main) {
           val data = gson.fromJson(apiRespository
               .doRequest(TheSportBDApi.getPlayer(league)).await(),
               PlayerResponse::class.java
           )


               view.showTeamPlayer(data.players!!)

       }
   }
}