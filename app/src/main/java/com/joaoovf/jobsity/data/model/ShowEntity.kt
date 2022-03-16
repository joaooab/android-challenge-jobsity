package com.joaoovf.jobsity.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "show")
data class ShowEntity(
	@PrimaryKey
	val id: Int,
	val name: String,
	@Embedded
	val image: ImageEntity,
	val genres: List<String>,
	@Embedded
	val schedule: ScheduleEntity,
	val summary: String,
)