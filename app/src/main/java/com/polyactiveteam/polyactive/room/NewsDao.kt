package com.polyactiveteam.polyactive.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NewsDao {
    @Query("select * from news where groupId = :groupId")
    fun getAllNewsFrom(groupId: Int): List<NewsEntity>

    @Query("select * from news")
    fun getAllNews(): List<NewsEntity>

    @Query("select * from news where groupId = :groupId limit :limit")
    fun getNews(groupId: Int, limit: Int): List<NewsEntity>

    @Query("select count(*) from news where groupId = :groupId")
    fun getCount(groupId: Int): Int

    @Query("select count(*) from news")
    fun getCount(): Int

    @Insert(onConflict = REPLACE)
    fun insertNews(newsEntity: NewsEntity)

    @Update
    fun updateNews(newsEntity: NewsEntity)

    @Delete
    fun deleteNews(newsEntity: NewsEntity)
}
