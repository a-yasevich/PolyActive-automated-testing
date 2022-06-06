package com.polyactiveteam.polyactive.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(ImageBitmapString::class)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao

    companion object {
        private var INSTANCE: CacheDatabase? = null

        fun getInstance(context: Context): CacheDatabase? {
            if (INSTANCE == null) {
                synchronized(CacheDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CacheDatabase::class.java, "news.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}