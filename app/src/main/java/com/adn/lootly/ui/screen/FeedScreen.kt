package com.adn.lootly.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adn.lootly.data.loadGiveaways
import com.adn.lootly.ui.components.GiveawayRow
import com.adn.lootly.ui.model.FeedUiState
import com.adn.lootly.ui.theme.LootlyTheme
import com.adn.lootly.ui.viewmodel.FeedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun FeedScreen(
    state: FeedUiState,
    onQueryChange: (String) -> Unit,
    onPlatformToggle: (String) -> Unit,
    onTypeToggle: (String) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    when(state){
        FeedUiState.Loading -> {
            Box(modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
        }

//        FeedUiState.Empty -> Box(modifier.fillMaxSize(), Alignment.Center) {
//            Text("Tidak ada giveaway yang cocok")
//        }

        is FeedUiState.Error -> {
            state.message
            Box(modifier.fillMaxSize(), Alignment.Center){
                Text(text = state.message)
            }
        }

        is FeedUiState.Content -> {
            var query by remember { mutableStateOf(state.query) }
            LaunchedEffect(state.query) {
                snapshotFlow { query }.debounce(300).distinctUntilChanged().collectLatest {
                    onQueryChange.invoke(it)
                }
            }
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = query,
                        onValueChange = {
                            query = it
                        },
                        label = { Text("Cari") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.options.forEach { option ->
                            FilterChip(
                                selected = option in state.platforms,
                                onClick = { onPlatformToggle(option) },
                                label = { Text(option) }
                            )
                        }
                    }
                }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.typeOptions.forEach { type ->
                            FilterChip(
                                selected = type in state.types,
                                onClick = { onTypeToggle(type) },
                                label = { Text(type) }
                            )
                        }
                    }
                }

                if(state.giveaways.isEmpty()){
                   item { Text(text = "Not Found") }
                }else {
                    items(state.giveaways, key = { it.id }) { giveaway ->
                        GiveawayRow(giveaway, onClick = { onItemClick(giveaway.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun FeedRoute(viewModel: FeedViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FeedScreen(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onPlatformToggle = viewModel::onPlatformToggle,
        onTypeToggle = viewModel::onTypeToggle,
        onItemClick = {}
    )
}