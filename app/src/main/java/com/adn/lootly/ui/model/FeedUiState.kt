package com.adn.lootly.ui.model

sealed interface FeedUiState{
    data object Loading : FeedUiState
    data class Content(
        val giveaways: List<GiveawayUi>,
        val query: String,
        val platforms: Set<String>,
        val types: Set<String>,
        val options: List<String>,
        val typeOptions: List<String>
    ) : FeedUiState
    data class Error(val message: String) : FeedUiState
}