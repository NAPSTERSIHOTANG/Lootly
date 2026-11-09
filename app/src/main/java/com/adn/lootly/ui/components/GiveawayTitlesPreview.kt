package com.adn.lootly.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.adn.lootly.data.loadGiveaways
import com.adn.lootly.data.model.Giveaway

@Preview(showBackground = true)
@Composable
fun GiveawayTitlesPreview(){
    val giveaways = loadGiveaways(LocalContext.current)
    Column {
        giveaways.forEach { Text(it.title) }
    }
}