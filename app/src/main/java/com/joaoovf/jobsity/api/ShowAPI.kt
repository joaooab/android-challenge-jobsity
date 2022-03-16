package com.joaoovf.jobsity.api

import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.SearchShow
import com.joaoovf.jobsity.domain.model.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowAPI {

	@GET("/shows")
	suspend fun fetchAll(@Query("page") page: Int): List<Show>

	@GET("/shows/{id}")
	suspend fun fetchById(@Path("id") id: Int): Show?

	@GET("/search/shows")
	suspend fun fetchByQuery(@Query("q") query: String?): List<SearchShow>

	@GET("/shows/{id}/episodes")
	suspend fun fetchAllEpisodes(@Path("id") id: Int): List<Episode>

}