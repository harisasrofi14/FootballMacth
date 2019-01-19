package com.example.haris.finalproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.example.haris.finalproject.activity.INTENT_TEAM_DETAIL_REQUEST_CODE
import com.example.haris.finalproject.activity.TeamDetailActivity
import com.example.haris.finalproject.adapter.TeamRecyclerViewAdapter
import com.example.haris.finalproject.helper.database
import com.example.haris.finalproject.model.TeamItem
import com.example.haris.finalproject.R
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity




class FavoriteTeamFragment : Fragment(){


    private var favorites: MutableList<TeamItem> = mutableListOf()
    private  var originFav : MutableList<TeamItem> = mutableListOf()
    private lateinit var searchView: SearchView
    private val favoriteAdapter =
       TeamRecyclerViewAdapter(favorites) { item: TeamItem -> itemClicked(item) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    private fun itemClicked(item: TeamItem) {
        startActivity<TeamDetailActivity>(INTENT_TEAM_DETAIL_REQUEST_CODE to item)
    }

    private fun showFavorite(){
        context?.database?.use {
            val result = select(TeamItem.TABLE_FAVORITES)
            val favorite = result.parseList(classParser<TeamItem>())
            favorites.addAll(favorite)
            favoriteAdapter.notifyDataSetChanged()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)
       // tv_toolbar_title.text = "Favorite Team"
        rv_favorite_team.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_favorite_team.adapter = favoriteAdapter
        favorites.clear()
        showFavorite()
    }

    override fun onResume() {
        favorites.clear()
        showFavorite()
        super.onResume()
    }

}
