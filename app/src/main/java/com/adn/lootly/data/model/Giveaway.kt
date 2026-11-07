package com.adn.lootly.data.model

data class Giveaway(
    val id: Int,
    val title: String,
    val worth: String,
    val thumbnail: String,
    val image: String,
    val description: String,
    val instruction: String,
    val type: String,
    val platform: String,
    val status: String,
    val open_giveaway_url: String,
    val gamerpower_url: String,
    val open_giveaway: String
)

//data class GiveawayResponse(val giveaway: List<Giveaway>)
