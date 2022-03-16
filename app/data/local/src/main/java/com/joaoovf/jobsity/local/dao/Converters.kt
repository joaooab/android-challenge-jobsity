package com.joaoovf.jobsity.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

	companion object {

		@TypeConverter
		@JvmStatic
		fun jsonToList(value: String?): List<String> {
			val type = object : TypeToken<List<String>>() {}.type
			return Gson().fromJson(value.orEmpty(), type)
		}

		@TypeConverter
		@JvmStatic
		fun listToJson(list: List<String>?) = Gson().toJson(list.orEmpty())

	}
}