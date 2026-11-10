package com.adn.lootly.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adn.lootly.data.loadGiveaways
import com.adn.lootly.ui.components.GiveawayRow
import com.adn.lootly.ui.model.FeedUiState
import com.adn.lootly.ui.theme.LootlyTheme

@Composable
fun FeedScreen(
    state: FeedUiState,
    onQueryChange: (String) -> Unit,
    onPlatformToggle: (String) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.items, key = { it.id }) { giveaway ->
            GiveawayRow(
                giveaway = giveaway,
                onClick = { onItemClick(giveaway.id) }
            )
        }
    }
}