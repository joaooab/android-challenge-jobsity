package com.joaoovf.jobsity.domain.model

data class Episode(
	val id: Int,
	val image: Image = Image(),
	val name: String = "",
	val number: Int = 0,
	val season: Int = 0,
	val summary: String = "",
)