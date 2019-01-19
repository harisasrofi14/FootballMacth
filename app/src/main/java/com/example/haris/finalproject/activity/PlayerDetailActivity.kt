package com.example.haris.finalproject.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.haris.finalproject.model.PlayerItem
import com.example.haris.finalproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.toolbar.*


const val INTENT_PLAYER_DETAIL_REQUEST_CODE = "3"
class PlayerDetailActivity : AppCompatActivity() {
    private lateinit var item: PlayerItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        item = intent.getParcelableExtra(INTENT_PLAYER_DETAIL_REQUEST_CODE)
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = "Player Detail"
        setupLayout(item)
    }

    private fun setupLayout(item: PlayerItem) {
            if (item.strCutout != null) {
                Picasso.get()
                    .load(item.strCutout)
                    .into(iv_player)
            }
            tvPlayerName.text = item.strPlayer
            tvPlayerTeam.text = item.strTeam
            tvPlayerPosition.text = item.strPosition
            tvPlayerHeight.text = item.strHeight
            tvPlayerWeight.text = item.strWeight
            tvPlayerDesc.text = item.strDescriptionEN
    }

}
