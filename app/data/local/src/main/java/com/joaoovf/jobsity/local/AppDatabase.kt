package com.joaoovf.jobsity.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joaoovf.jobsity.local.dao.AuthDAO
import com.joaoovf.jobsity.local.dao.Converters
import com.joaoovf.jobsity.local.dao.ShowDAO
import com.joaoovf.jobsity.local.model.AuthEntity
import com.joaoovf.jobsity.local.model.ShowEntity

@Database(
	version = 1,
	entities = [
		ShowEntity::class,
		AuthEntity::class,
	],
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun showDAO(): ShowDAO
	abstract fun authDAO(): AuthDAO

	companion object {
		private const val DATABASE_NAME = "databasejobsity.db"

		@Volatile
		private var instance: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase {
			return instance ?: synchronized(this) {
				instance ?: buildDatabase(context).also { instance = it }
			}
		}

		private fun buildDatabase(context: Context): AppDatabase {
			return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
				.allowMainThreadQueries()
				.fallbackToDestructiveMigration()
				.build()
		}
	}

}
