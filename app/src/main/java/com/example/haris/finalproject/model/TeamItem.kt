package com.example.haris.finalproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamItem(val id: Long?,
                    val idTeam: String?,
                    val strTeamBadge: String?,
                    val strDescriptionEN : String?,
                    val strTeam : String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITES = "FavoriteTeamDB"
        const val ID = "ID"
        const val ID_TEAM = "ID_TEAM"
        const val TEAM_DESC = "TEAM_DESC"
        const val TEAM_BADGE = "TEAM_BADGE"
        const val TEAM="TEAM"
    }
}