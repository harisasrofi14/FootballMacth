package com.example.haris.finalproject.Interface

import com.example.haris.finalproject.model.LeagueResponse
import com.example.haris.finalproject.model.TeamItem

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
    fun showTeamList(data: List<TeamItem>)
}