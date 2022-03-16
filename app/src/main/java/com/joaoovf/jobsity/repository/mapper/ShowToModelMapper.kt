package com.joaoovf.jobsity.repository.mapper

import com.joaoovf.jobsity.api.model.ImageNetwork
import com.joaoovf.jobsity.api.model.ScheduleNewtork
import com.joaoovf.jobsity.api.model.ShowNetwork
import com.joaoovf.jobsity.data.model.ImageEntity
import com.joaoovf.jobsity.data.model.ScheduleEntity
import com.joaoovf.jobsity.data.model.ShowEntity
import com.joaoovf.jobsity.domain.model.Image
import com.joaoovf.jobsity.domain.model.Schedule
import com.joaoovf.jobsity.domain.model.Show

//Local

fun ShowEntity.toModel() = Show(
	id = id,
	name = name,
	image = image.toModel(),
	genres = genres,
	schedule = schedule.toModel(),
	summary = summary
)

fun ScheduleEntity.toModel() = Schedule(
	days = days,
	time = time,
)

fun ImageEntity.toModel() = Image(
	medium = medium,
	original = original,
)

//Remote

fun ShowNetwork.toModel() = Show(
	id = id,
	name = name.orEmpty(),
	image = image?.toModel() ?: Image(),
	genres = genres.orEmpty(),
	schedule = schedule?.toModel() ?: Schedule(),
	summary = summary.orEmpty()
)

fun ScheduleNewtork.toModel() = Schedule(
	days = days.orEmpty(),
	time = time.orEmpty(),
)

fun ImageNetwork.toModel() = Image(
	medium = medium,
	original = original,
)