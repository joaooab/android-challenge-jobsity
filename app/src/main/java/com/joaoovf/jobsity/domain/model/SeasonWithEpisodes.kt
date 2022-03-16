package com.joaoovf.jobsity.domain.model

data class SeasonWithEpisodes(
	val season: Int,
	val episodes: List<Episode>
)