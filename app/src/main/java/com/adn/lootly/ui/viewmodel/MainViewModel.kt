package com.adn.lootly.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adn.lootly.data.model.Giveaway
import com.adn.lootly.data.remote.giveawayService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _giveawayState = mutableStateOf(GiveawayState())
    val giveawayState: State<GiveawayState> = _giveawayState

    init {
        fetchGiveaways()
    }

    private fun fetchGiveaways(
        platforms: String? = null,
        type: String? = null
        ){
        viewModelScope.launch {
            try {
                val response = giveawayService.filterGiveaways(platforms.toString(), type.toString())
                _giveawayState.value = _giveawayState.value.copy(
                    list = response.giveaway,
                    loading = false
                )
            }catch (e: Exception){
                _giveawayState.value = _giveawayState.value.copy(
                    loading = false,
                    error = "Error fetching giveaways: ${e.message}"
                )
            }
        }

    }


    data class GiveawayState(
        val loading: Boolean = true,
        val list: List<Giveaway> = emptyList(),
        val error: String? = null
    )
}