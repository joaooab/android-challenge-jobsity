package com.joaoovf.jobsity.repository.mapper

import com.joaoovf.jobsity.data.model.ImageEntity
import com.joaoovf.jobsity.data.model.ScheduleEntity
import com.joaoovf.jobsity.data.model.ShowEntity
import com.joaoovf.jobsity.domain.model.Image
import com.joaoovf.jobsity.domain.model.Schedule
import com.joaoovf.jobsity.domain.model.Show

fun Show.toEntity() = ShowEntity(
	id = id,
	name = name,
	image = image.toEntity(),
	genres = genres,
	schedule = schedule.toEntity(),
	summary = summary
)

fun Schedule.toEntity() = ScheduleEntity(
	days = days,
	time = time,
)

fun Image.toEntity() = ImageEntity(
	medium = medium,
	original = original,
)