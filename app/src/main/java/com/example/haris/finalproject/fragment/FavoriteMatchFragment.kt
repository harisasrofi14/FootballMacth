package com.example.haris.finalproject.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.example.haris.finalproject.activity.INTENT_DETAIL_REQUEST_CODE
import com.example.haris.finalproject.activity.MatchDetailActivity
import com.example.haris.finalproject.adapter.MatchRecyclerViewAdapter
import com.example.haris.finalproject.helper.database
import com.example.haris.finalproject.model.EventItem

import com.example.haris.finalproject.R
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity


class FavoriteMatchFragment : Fragment()  {

    private var favorites: MutableList<EventItem> = mutableListOf()
    private  var originFav : MutableList<EventItem> = mutableListOf()
    private lateinit var searchView: SearchView
    private val favoriteAdapter =
        MatchRecyclerViewAdapter(favorites) { item: EventItem -> itemClicked(item) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }


    private fun showFavorite(){
        context?.database?.use {
            val result = select(EventItem.TABLE_FAVORITES)
            val favorite = result.parseList(classParser<EventItem>())
            favorites.clear()
            favorites.addAll(favorite)
            originFav.clear()
            originFav.addAll(favorite)
            favoriteAdapter.notifyDataSetChanged()
        }
    }
    private fun itemClicked(item: EventItem) {
        startActivity<MatchDetailActivity>(INTENT_DETAIL_REQUEST_CODE to item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(null)
        rv_favorite_match.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_favorite_match.adapter = favoriteAdapter
        favorites.clear()
        showFavorite()
    }

    override fun onResume()
    {
        favorites.clear()
        showFavorite()
        super.onResume()
    }


}
