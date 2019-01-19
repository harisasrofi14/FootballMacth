package com.example.haris.finalproject.Interface

import com.example.haris.finalproject.model.TeamItem

interface TeamDescView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(team: List<TeamItem>)
}