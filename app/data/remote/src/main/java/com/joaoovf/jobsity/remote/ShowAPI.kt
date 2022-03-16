package com.joaoovf.jobsity.remote

import com.joaoovf.jobsity.remote.model.EpisodeNetwork
import com.joaoovf.jobsity.remote.model.SearchShowNewtork
import com.joaoovf.jobsity.remote.model.ShowNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowAPI {

	@GET("/shows")
	suspend fun fetchAll(@Query("page") page: Int): List<ShowNetwork>

	@GET("/shows/{id}")
	suspend fun fetchById(@Path("id") id: Int): ShowNetwork?

	@GET("/search/shows")
	suspend fun fetchByQuery(@Query("q") query: String?): List<SearchShowNewtork>

	@GET("/shows/{id}/episodes")
	suspend fun fetchAllEpisodes(@Path("id") id: Int): List<EpisodeNetwork>

	@GET("/episodes/{id}")
	suspend fun fetchEpisodeById(@Path("id") id: Int): EpisodeNetwork?

}