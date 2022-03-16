package com.joaoovf.jobsity.api

import com.joaoovf.jobsity.api.model.EpisodeNetwork
import com.joaoovf.jobsity.api.model.SearchShowNewtork
import com.joaoovf.jobsity.api.model.ShowNetwork
import com.joaoovf.jobsity.domain.model.Episode
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