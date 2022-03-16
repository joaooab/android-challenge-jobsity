package com.joaoovf.jobsity.data.dao

import androidx.room.*
import com.joaoovf.jobsity.data.model.ShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDAO {

	@Query("SELECT * FROM show WHERE id =:id")
	fun loadById(id: Int): ShowEntity?

	@Query("SELECT * FROM show")
	fun loadAll(): Flow<List<ShowEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun save(show: ShowEntity)

	@Delete
	fun delete(show: ShowEntity)

	@Update
	fun update(show: ShowEntity)

}