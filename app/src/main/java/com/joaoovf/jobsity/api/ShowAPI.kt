package com.joaoovf.jobsity.api

import com.joaoovf.jobsity.domain.model.SearchShow
import com.joaoovf.jobsity.domain.model.Show
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowAPI {

	@GET("/shows")
	suspend fun fetchAll(@Query("page") page: Int): List<Show>

	@GET("/search/shows")
	suspend fun fetchByQuery(@Query("q") query: String?): List<SearchShow>

}