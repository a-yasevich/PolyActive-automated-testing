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

data class News(
    val id: Int,
    val imageURL: URL?,
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
            var imageURL: URL? = null

            if (attachments.length() != 0) {
                try {
                    val jsonObject = (attachments.get(0) as JSONObject)
                        .getJSONObject("photo")
                    imageURL = URL(
                        (jsonObject
                            .getJSONArray("sizes")
                            .get(2) as JSONObject)
                            .getString("url")
                    )
                } catch (e: JSONException) {
                }
            }

            val dateLong = json.getLong("date")
            val date: String = formatter.format(Date(dateLong * 1000))
            val likes: Int = json.getJSONObject("likes").getInt("count")
            return News(id, imageURL, null, header, text, date, dateLong, likes)
        }
    }

    fun getImageBitmap(): Bitmap? {
        if (image != null) {
            return image
        }

        if (imageURL != null) {
            val image: Bitmap = service.submit<Bitmap> {
                return@submit BitmapFactory.decodeStream(
                    imageURL.openConnection().getInputStream()
                )
            }.get()
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