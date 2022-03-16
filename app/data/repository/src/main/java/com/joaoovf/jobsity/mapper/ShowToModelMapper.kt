package com.joaoovf.jobsity.mapper

import com.joaoovf.jobsity.remote.model.ImageNetwork
import com.joaoovf.jobsity.remote.model.ScheduleNewtork
import com.joaoovf.jobsity.remote.model.ShowNetwork
import com.joaoovf.jobsity.local.model.ImageEntity
import com.joaoovf.jobsity.local.model.ScheduleEntity
import com.joaoovf.jobsity.local.model.ShowEntity
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