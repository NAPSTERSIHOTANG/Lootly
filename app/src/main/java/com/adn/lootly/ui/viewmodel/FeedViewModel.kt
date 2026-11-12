package com.adn.lootly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adn.lootly.ui.model.FeedUiState
import com.adn.lootly.ui.model.GiveawayUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val loadItems: () -> List<GiveawayUi>
) : ViewModel(){
    private sealed interface Source{
        data object Loading: Source
        data class Loaded(val items: List<GiveawayUi>) : Source
        data class Failed(val message: String) : Source
    }

    private val source = MutableStateFlow<Source>(Source.Loading)
    private val query = MutableStateFlow("")
    private val platforms = MutableStateFlow<Set<String>>(emptySet())
    private val types = MutableStateFlow<Set<String>>(emptySet())

    val uiState: StateFlow<FeedUiState> = combine(source, query, platforms, types) { src, q, p, t ->
        when (src){
            Source.Loading -> FeedUiState.Loading
            is Source.Failed -> FeedUiState.Error(src.message)
            is Source.Loaded -> {
                val filtered = src.items.filter { item ->
                        item.title.contains(q, ignoreCase = true) &&
                        (p.isEmpty() || item.platformList().any { it in p }) &&
                        (t.isEmpty() || item.type in t)
                }
//                if (filtered.isEmpty()) FeedUiState
                FeedUiState.Content(
                    giveaways = filtered,
                    query = q,
                    platforms = p,
                    types = t,
                    options = src.items.flatMap { it.platformList() }.distinct().sorted()
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FeedUiState.Loading
        )

    init {
        viewModelScope.launch {
            delay(800) // fixture instan, delay supaya Loading sempat terlihat
            source.value = try {
                Source.Loaded(loadItems())
            } catch (e: Exception) {
                Source.Failed(e.message ?: "Gagal memuat data")
            }
        }
    }
    fun onQueryChange(value: String) { query.value = value }
    fun onPlatformToggle(name: String) { platforms.update { it.toggle(name) } }
    fun onTypeToggle(name: String) { types.update { it.toggle(name) } }

    private fun Set<String>.toggle(value: String) =
        if (value in this) this - value else this + value

    private fun GiveawayUi.platformList() =
        platforms.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}