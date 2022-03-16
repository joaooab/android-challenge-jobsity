package com.joaoovf.jobsity.domain.model

data class Schedule(
	val days: List<String> = listOf(),
	val time: String = ""
) {

	fun format(): String = "${days.joinToString()} $time"

}