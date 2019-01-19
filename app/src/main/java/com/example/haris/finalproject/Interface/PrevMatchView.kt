package com.example.haris.finalproject.Interface

import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.model.LeagueResponse

interface PrevMatchView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
    fun showEventListPrev(data: List<EventItem>)
}