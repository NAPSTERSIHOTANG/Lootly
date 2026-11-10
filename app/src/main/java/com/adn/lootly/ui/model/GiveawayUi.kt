package com.adn.lootly.ui.model

import com.adn.lootly.data.model.GiveawayDto

data class GiveawayUi(
    val id: Int,
    val title: String,
    val worth: String,
    val platforms: String
)

fun GiveawayDto.toEntity(): GiveawayUi{
    return GiveawayUi(
        id = this.id,
        title = this.title,
        worth = this.worth,
        platforms = this.platforms,
    )
}
