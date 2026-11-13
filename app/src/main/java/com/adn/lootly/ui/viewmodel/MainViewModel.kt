package com.adn.lootly.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adn.lootly.data.model.GiveawayResponse
import com.adn.lootly.data.remote.giveawayService
import com.adn.lootly.ui.model.GiveawayUi
import com.adn.lootly.ui.model.toEntity
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _giveawayState = mutableStateOf(GiveawayState())
    val giveawayState: State<GiveawayState> = _giveawayState

    init {
//        fetchGiveaways()
        getGiveaways()
    }

    private fun getGiveaways(){
        viewModelScope.launch {
            try {
                val response = giveawayService.getGiveaways()
                _giveawayState.value = _giveawayState.value.copy(
                    list = response.map { it.toEntity() },
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _giveawayState.value = _giveawayState.value.copy(
                    loading = false,
                    error = "Error fetching giveaways: ${e.message}"
                )
            }
        }

    }

    private fun fetchGiveaways(
        platforms: String? = null,
        type: String? = null
        ){
        viewModelScope.launch {
            try {
                val response = giveawayService.filterGiveaways(platforms, type)
                _giveawayState.value = _giveawayState.value.copy(
                    list = response.map { it.toEntity() },
                    loading = false,
                    error = null
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
        val list: List<GiveawayUi> = emptyList(),
        val error: String? = null
    )
}