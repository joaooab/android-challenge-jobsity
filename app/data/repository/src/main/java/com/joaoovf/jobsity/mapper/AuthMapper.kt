package com.joaoovf.jobsity.mapper

import com.joaoovf.jobsity.domain.model.Auth
import com.joaoovf.jobsity.local.model.AuthEntity

fun AuthEntity.toModel() = Auth(
	username = username,
	password = password
)

fun Auth.toEntity() = AuthEntity(
	username = username,
	password = password
)