package com.adn.lootly.ui.model

import com.adn.lootly.data.model.GiveawayResponse

data class GiveawayUi(
    val id: Int,
    val title: String,
    val worth: String,
    val platforms: String,
    val type: String
)

fun GiveawayResponse.toEntity(): GiveawayUi{
    return GiveawayUi(
        id = this.id ?: -1,
        title = this.title ?: "untitle",
        worth = this.worth ?: "-",
        platforms = this.platforms ?: "No platform",
        type = this.type.orEmpty()
    )
}