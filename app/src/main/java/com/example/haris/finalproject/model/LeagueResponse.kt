package com.example.haris.finalproject.model

import com.google.gson.annotations.SerializedName

data class LeagueResponse(@SerializedName("leagues")val leagues: List<LeagueItem>?)
