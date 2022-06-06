package com.polyactiveteam.polyactive.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future

data class News(
    val id: Int,
    val imageFuture: Future<Bitmap>?,
    var image: Bitmap?,
    val header: String,
    val newsDescription: String,
    val date: String,
    val dateLong: Long,
    val likeCounter: Int
) {
    companion object {
        private val formatter: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        private val service = Executors.newFixedThreadPool(4)

        fun getNewsFromJson(json: JSONObject): News {
            val id: Int = json.getInt("id")
            val text: String = json.getString("text")
            val header = text.substring(0, 20) + "..."
            val attachments: JSONArray = json.getJSONArray("attachments")
            var imageFuture: Future<Bitmap>? = null

            if (attachments.length() != 0) {
                try {
                    val jsonObject = (attachments.get(0) as JSONObject)
                        .getJSONObject("photo")
                    val imageUrl = URL(
                        (jsonObject
                            .getJSONArray("sizes")
                            .get(2) as JSONObject)
                            .getString("url")
                    )
                    imageFuture = service.submit<Bitmap> {
                        return@submit BitmapFactory.decodeStream(
                            imageUrl.openConnection().getInputStream()
                        )
                    }
                } catch (e: JSONException) {
                }
            }

            val dateLong = json.getLong("date")
            val date: String = formatter.format(Date(dateLong * 1000))
            val likes: Int = json.getJSONObject("likes").getInt("count")
            return News(id, imageFuture, null, header, text, date, dateLong, likes)
        }
    }

    fun getImageBitmap(): Bitmap? {
        if (image != null) {
            return image
        }

        if (imageFuture != null) {
            val image: Bitmap = imageFuture.get()
            this.image = image
            return image
        }

        return null
    }

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (id != other.id) return false

        return true
    }
}