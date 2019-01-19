package com.example.haris.finalproject.model

data class LeagueItem(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}