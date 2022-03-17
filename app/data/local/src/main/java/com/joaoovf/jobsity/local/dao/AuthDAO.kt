package com.joaoovf.jobsity.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joaoovf.jobsity.local.model.AuthEntity

@Dao
interface AuthDAO {

	@Query("SELECT * FROM auth WHERE username =:username")
	fun loadByUsername(username: String): AuthEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun save(auth: AuthEntity)

}