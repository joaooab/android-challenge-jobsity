package com.joaoovf.jobsity.api.model

data class EpisodeNetwork(
	val id: Int,
	val image: ImageNetwork?,
	val name: String?,
	val number: Int?,
	val season: Int?,
	val summary: String?,
)