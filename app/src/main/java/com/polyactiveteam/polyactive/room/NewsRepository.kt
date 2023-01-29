package com.polyactiveteam.polyactive.room

import android.content.Context

class NewsRepository(context: Context) {
    private val dao: NewsDao = CacheDatabase.getInstance(context)?.newsDao()!!

    fun getAllNewsFrom(groupId: Int): List<NewsEntity> {
        return doSelectInThread { dao.getAllNewsFrom(groupId) }
    }

    fun getAllNews(): List<NewsEntity> {
        return doSelectInThread { dao.getAllNews() }
    }

    fun getNews(groupId: Int, limit: Int): List<NewsEntity> {
        return doSelectInThread { dao.getNews(groupId, limit) }
    }

    fun getCount(groupId: Int): Int {
        var res = 0
        val thread = Thread {
            res = dao.getCount(groupId)
        }

        thread.start()
        thread.join()
        return res
    }

    fun getCount(): Int {
        var res = 0
        val thread = Thread {
            res = dao.getCount()
        }

        thread.start()
        thread.join()
        return res
    }

    private fun doSelectInThread(query: () -> List<NewsEntity>): List<NewsEntity> {
        val result = mutableListOf<NewsEntity>()

        val thread = Thread {
            result.addAll(query.invoke())
        }

        thread.start()
        thread.join()
        return result
    }

    fun deleteNews(news: List<NewsEntity>) {
        doInThread(news) {
            dao.deleteNews(it)
        }
    }

    fun insertNews(news: List<NewsEntity>) {
        doInThread(news) {
            dao.insertNews(it)
        }
    }

    fun updateNews(news: List<NewsEntity>) {
        doInThread(news) {
            dao.updateNews(it)
        }
    }

    private fun doInThread(news: List<NewsEntity>, query: (NewsEntity) -> Unit) {
        val thread = Thread {
            news.forEach {
                query.invoke(it)
            }
        }

        thread.start()
    }
}