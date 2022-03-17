package com.joaoovf.jobsity.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthEntity(
	@PrimaryKey
	val username: String,
	val password: String,
)
