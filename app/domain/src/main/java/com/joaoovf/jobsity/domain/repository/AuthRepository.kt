package com.joaoovf.jobsity.domain.repository

import com.joaoovf.jobsity.domain.model.Auth


interface AuthRepository {

	fun loadByUsername(username: String): Auth?

	fun save(auth: Auth)

}
