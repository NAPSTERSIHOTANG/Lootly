package com.adn.lootly.ui.model

//data class FeedUiState(
//    val query: String = "",
//    val selectedPlatforms: Set<String> = emptySet(),
//    val items: List<GiveawayUi> = emptyList()
//)

sealed interface FeedUiState{
    data object Loading : FeedUiState
    data class Content(
        val giveaways: List<GiveawayUi>,
        val query: String,
        val platforms: Set<String>,
        val types: Set<String>,
        val options: List<String>
    ) : FeedUiState
    data object Empty : FeedUiState
    data class Error(val message: String) : FeedUiState
}