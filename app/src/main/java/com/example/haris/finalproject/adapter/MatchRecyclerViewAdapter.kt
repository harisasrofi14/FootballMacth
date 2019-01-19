package com.example.haris.finalproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.R
import kotlinx.android.synthetic.main.list_item_match.view.*
import java.util.ArrayList

class MatchRecyclerViewAdapter(private val items:List<EventItem>,private val clickListener : (EventItem) -> Unit): RecyclerView.Adapter<MatchRecyclerViewAdapter.MatchHolder>() {
    private var arrayList: ArrayList<EventItem>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MatchHolder {
        return MatchHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.list_item_match,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        holder.bindMatch(items[position],clickListener)
    }

    fun setFilter(filterList: ArrayList<EventItem>) {
        arrayList = ArrayList<EventItem>()
        arrayList!!.addAll(filterList)
        notifyDataSetChanged()
    }

    class MatchHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvHomeTeam = view.tvHomeTeam
        private val tvDate = view.tvDate
        private val tvHomeScore = view.tvHomeScore
        private val tvAwayTeam = view.tvAwayTeam
        private val tvAwayScore = view.tvAwayScore


        fun bindMatch(match: EventItem,clickListener: (EventItem) -> Unit) {
            tvHomeTeam.text =  match.strHomeTeam
            tvDate.text = match.dateEvent.toString()
            tvHomeScore.text = match.intHomeScore
            tvAwayTeam.text = match.strAwayTeam
            tvAwayScore.text = match.intAwayScore
            itemView.setOnClickListener { clickListener(match) }
            tvAwayScore.setOnClickListener {

            }
        }
    }


}