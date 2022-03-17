package com.joaoovf.jobsity

import com.joaoovf.jobsity.domain.model.Auth
import com.joaoovf.jobsity.domain.repository.AuthRepository
import com.joaoovf.jobsity.local.dao.AuthDAO
import com.joaoovf.jobsity.mapper.toEntity
import com.joaoovf.jobsity.mapper.toModel

class AuthRepositoryImpl(private val authDAO: AuthDAO) : AuthRepository {

	override fun loadByUsername(username: String): Auth? {
		return authDAO.loadByUsername(username)?.toModel()
	}

	override fun save(auth: Auth) {
		return authDAO.save(auth.toEntity())
	}

}