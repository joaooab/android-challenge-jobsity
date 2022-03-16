package com.joaoovf.jobsity.domain.model

data class Show(
    val id: Int,
    val name: String,
    val image: Image?,
    val schedule: Schedule,
    val genres: List<String>,
    val summary: String,
)