package com.example.haris.finalproject.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.model.TeamItem
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx,"FavoriteMatch.db",null,1){
    companion object {
        private var instance : MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null){
                instance =
                        MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            EventItem.TABLE_FAVORITES, true,
            EventItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            EventItem.ID_EVENT to TEXT,
            EventItem.DATE to TEXT,
            EventItem.TIME to TEXT,

            // home team
            EventItem.HOME_ID to TEXT,
            EventItem.HOME_TEAM to TEXT,
            EventItem.HOME_SCORE to TEXT,
            EventItem.HOME_FORMATION to TEXT,
            EventItem.HOME_GOAL_DETAILS to TEXT,
            EventItem.HOME_SHOTS to TEXT,
            EventItem.HOME_LINEUP_GOALKEEPER to TEXT,
            EventItem.HOME_LINEUP_DEFENSE to TEXT,
            EventItem.HOME_LINEUP_MIDFIELD to TEXT,
            EventItem.HOME_LINEUP_FORWARD to TEXT,
            EventItem.HOME_LINEUP_SUBSTITUTES to TEXT,

            // away team
            EventItem.AWAY_ID to TEXT,
            EventItem.AWAY_TEAM to TEXT,
            EventItem.AWAY_SCORE to TEXT,
            EventItem.AWAY_FORMATION to TEXT,
            EventItem.AWAY_GOAL_DETAILS to TEXT,
            EventItem.AWAY_SHOTS to TEXT,
            EventItem.AWAY_LINEUP_GOALKEEPER to TEXT,
            EventItem.AWAY_LINEUP_DEFENSE to TEXT,
            EventItem.AWAY_LINEUP_MIDFIELD to TEXT,
            EventItem.AWAY_LINEUP_FORWARD to TEXT,
            EventItem.AWAY_LINEUP_SUBSTITUTES to TEXT
        )

        db.createTable(
            TeamItem.TABLE_FAVORITES,true,
            TeamItem.ID to INTEGER + PRIMARY_KEY+ AUTOINCREMENT,
            TeamItem.ID_TEAM to TEXT,
            TeamItem.TEAM_BADGE to TEXT,
            TeamItem.TEAM_DESC to TEXT,
            TeamItem.TEAM to TEXT
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventItem.TABLE_FAVORITES,true)
        db.dropTable(TeamItem.TABLE_FAVORITES,true)
    }

}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)