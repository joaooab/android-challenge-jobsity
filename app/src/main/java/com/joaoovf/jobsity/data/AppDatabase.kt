package com.joaoovf.jobsity.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joaoovf.jobsity.data.dao.Converters
import com.joaoovf.jobsity.data.dao.ShowDAO
import com.joaoovf.jobsity.data.model.ShowEntity

@Database(
	version = 1,
	entities = [
		ShowEntity::class,
	],
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun showDAO(): ShowDAO

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
				.build()
		}
	}

}
