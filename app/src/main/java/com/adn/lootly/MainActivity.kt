package com.adn.lootly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.adn.lootly.data.loadGiveaways
import com.adn.lootly.ui.model.FeedUiState
import com.adn.lootly.ui.model.toEntity
import com.adn.lootly.ui.screen.FeedRoute
import com.adn.lootly.ui.screen.FeedScreen
import com.adn.lootly.ui.screen.giveawayScreen
import com.adn.lootly.ui.theme.LootlyTheme
import com.adn.lootly.ui.viewmodel.FeedViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: FeedViewModel by viewModels {
        viewModelFactory {
            initializer {
                FeedViewModel { loadGiveaways(applicationContext).map { it.toEntity() } }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LootlyTheme {
                Surface(Modifier.fillMaxSize()) { FeedRoute(viewModel) }
                }
            }
        }
}