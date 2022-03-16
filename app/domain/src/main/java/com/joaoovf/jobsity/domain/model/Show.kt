package com.joaoovf.jobsity.domain.model

data class Show(
	val id: Int,
	val name: String = "",
	val image: Image = Image(),
	val schedule: Schedule = Schedule(),
	val genres: List<String> = listOf(),
	val summary: String = "",
)