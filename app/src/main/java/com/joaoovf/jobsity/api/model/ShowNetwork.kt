package com.joaoovf.jobsity.api.model

data class ShowNetwork(
	val id: Int,
	val name: String?,
	val image: ImageNetwork?,
	val schedule: ScheduleNewtork?,
	val genres: List<String>?,
	val summary: String?,
)