package com.joaoovf.jobsity.mapper

import com.joaoovf.jobsity.remote.model.EpisodeNetwork
import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Image

fun EpisodeNetwork.toModel() = Episode(
	id = id,
	image = image?.toModel() ?: Image(),
	name = name.orEmpty(),
	number = number ?: -1,
	season = season ?: -1,
	summary = summary.orEmpty()
)