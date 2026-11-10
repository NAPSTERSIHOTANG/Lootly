package com.adn.lootly.ui.model

data class FeedUiState(
    val query: String = "",
    val selectedPlatforms: Set<String> = emptySet(),
    val items: List<GiveawayUi> = emptyList()
)