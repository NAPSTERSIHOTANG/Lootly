package com.adn.lootly.data.remote

import com.adn.lootly.data.model.Giveaway
//import com.adn.lootly.data.model.GiveawayResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://www.gamerpower.com/api/").addConverterFactory(
    GsonConverterFactory.create()).build()

val giveawayService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("giveaways")
    suspend fun getGiveaways(): List<Giveaway>

    @GET("giveaway")
    suspend fun getGiveawayById(
        @Query("id") id: Int
    ): Giveaway

    @GET("filter")
    suspend fun filterGiveaways(
        @Query("platform") platforms: String? = null,
        @Query("type") types: String? = null
    ): List<Giveaway>
}