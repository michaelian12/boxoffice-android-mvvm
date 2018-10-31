package com.michaelagustian.boxoffice.data.source.local

import android.arch.persistence.room.*
import android.content.Context
import com.michaelagustian.boxoffice.data.TypeConverter
import com.michaelagustian.boxoffice.data.model.Movie
import com.michaelagustian.boxoffice.data.source.local.movie.MovieDao

/**
 * Created by Michael Agustian on 30/10/18.
 */

@Database(entities = [(Movie::class)], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "BoxOffice.db").build().also {
                    INSTANCE = it
                }

                return INSTANCE!!
            }
        }
    }
}