package com.example.haris.finalproject.API

import com.example.haris.finalproject.BuildConfig

object TheSportBDApi {

    fun getLeagueAll(): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/all_leagues.php"
    }

    fun getPastLeague(id: String): String{
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=${id}"
    }

    fun getNextLeague(id: String): String{
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=${id}"
    }

    fun getTeamDetails(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookupteam.php?id=${id}"
    }

    fun getTeam(id: String):String{
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/search_all_teams.php?l=${id}"
    }


   fun getPlayer(id: String): String {
       return BuildConfig.BASE_URL + BuildConfig.TSDB_API_KEY + "/lookup_all_players.php?id="+id
   }
}