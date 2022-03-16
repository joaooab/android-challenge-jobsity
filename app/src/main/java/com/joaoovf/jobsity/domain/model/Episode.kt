package com.joaoovf.jobsity.domain.model

data class Episode(
	val id: Int,
	val image: Image,
	val name: String,
	val number: Int,
	val season: Int,
	val summary: String,
)