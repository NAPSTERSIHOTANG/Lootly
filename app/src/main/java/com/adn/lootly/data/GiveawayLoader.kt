package com.adn.lootly.data

import android.content.Context
import com.adn.lootly.data.model.GiveawayDto
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun loadGiveaways(context: Context): List<GiveawayDto> {
    val text = context.assets.open("giveaways.json")
        .bufferedReader()
        .use { it.readText() }
    return json.decodeFromString<List<GiveawayDto>>(text)
}