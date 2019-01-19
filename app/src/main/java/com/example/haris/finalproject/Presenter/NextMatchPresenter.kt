package com.example.haris.finalproject.Presenter

import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.API.TheSportBDApi
import com.example.haris.finalproject.Interface.NextMatchView
import com.example.haris.finalproject.model.EventResponse
import com.example.haris.finalproject.model.LeagueResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NextMatchPresenter(private val view: NextMatchView, private val apiRespository: ApiRespository,
                         private val gson: Gson
) {
    fun getLeagueAll() {


        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(
                apiRespository
                    .doRequest(TheSportBDApi.getLeagueAll()).await(),
                LeagueResponse::class.java
            )

            view.showLeagueList(data)
        }
    }



    fun getNextEvent(id: String){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRespository
                    .doRequest(TheSportBDApi.getNextLeague(id)).await(),
                    EventResponse::class.java
                )





            try {
                view.showEventListNext(data.events!!)
                view.hideLoading()
            } catch (e: NullPointerException) {

            }
        }
    }
}

