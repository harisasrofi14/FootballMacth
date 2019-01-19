package com.example.haris.finalproject.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse( @SerializedName("player")val players: List<PlayerItem>?)