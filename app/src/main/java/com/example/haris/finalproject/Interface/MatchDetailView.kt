package com.example.haris.finalproject.Interface

import com.example.haris.finalproject.model.TeamItem

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamItem>, dataAwayTeam: List<TeamItem>)
}