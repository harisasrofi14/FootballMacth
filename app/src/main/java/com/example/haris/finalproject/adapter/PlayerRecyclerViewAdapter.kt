package com.example.haris.finalproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haris.finalproject.model.PlayerItem
import com.example.haris.finalproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_player.view.*

class PlayerRecyclerViewAdapter(private val items:List<PlayerItem>, private val clickListener : (PlayerItem) -> Unit):
    RecyclerView.Adapter<PlayerRecyclerViewAdapter.PlayerHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): PlayerRecyclerViewAdapter.PlayerHolder {
        return PlayerRecyclerViewAdapter.PlayerHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.list_item_player,
                viewGroup,
                false
            )
        )
    }



    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bindMatch(items[position],clickListener)
    }

    class PlayerHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivPlayerThumb = view.iv_player_thumb
        private val tvPlayerName = view.tv_player_name
        private val tvPlayerPosition = view.tv_player_position


        fun bindMatch(players: PlayerItem,clickListener: (PlayerItem) -> Unit) {
            if(players.strCutout != null) {
                Picasso.get().load(players.strCutout).into(ivPlayerThumb)
            }
            tvPlayerName.text = players.strPlayer
            tvPlayerPosition.text = players.strPosition
            itemView.setOnClickListener { clickListener(players) }
        }
    }
}