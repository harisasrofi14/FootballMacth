package com.example.haris.finalproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haris.finalproject.model.TeamItem
import com.example.haris.finalproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_team.view.*

class TeamRecyclerViewAdapter(private val items:List<TeamItem>, private val clickListener : (TeamItem) -> Unit):
    RecyclerView.Adapter<TeamRecyclerViewAdapter.TeamHolder>() {
    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        holder.bindMatch(items[position],clickListener)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): TeamRecyclerViewAdapter.TeamHolder {
        return TeamRecyclerViewAdapter.TeamHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.list_item_team,
                viewGroup,
                false
            )
        )
    }



    class TeamHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivTeamBadge = view.iv_team_badge
        private val tvTeamName = view.tv_team_name


        fun bindMatch(teams: TeamItem,clickListener: (TeamItem) -> Unit) {
            Picasso.get().load(teams.strTeamBadge).into(ivTeamBadge)
            tvTeamName.text = teams.strTeam
            itemView.setOnClickListener { clickListener(teams) }
        }
    }
    }