package com.michaelagustian.boxoffice.data

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

/**
 * Created by Michael Agustian on 31/10/18.
 */

object TypeConverter {

    private var gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        data?.let {
            val listType = object : TypeToken<List<Int>>() {}.type

            return gson.fromJson(data, listType)
        }

        return null
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        ints?.let {
            return gson.toJson(it)
        }

        return null
    }
}