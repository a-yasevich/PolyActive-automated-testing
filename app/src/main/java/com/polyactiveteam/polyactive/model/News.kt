package com.polyactiveteam.polyactive.model

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

data class News(
    val imageLink: String?,
    val header: String,
    val newsDescription: String,
    val date: String,
    val likeCounter: Int
) {
    companion object {
        private val formatter: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

        fun getNewsFromJson(json: JSONObject): News {
            val text: String = json.getString("text")
            val header = text.substring(0, 20) + "..."
            val attachments: JSONArray = json.getJSONArray("attachments")
            var imageLink: String? = null
            if (attachments.length() != 0) {
                try {
                    val jsonObject = (attachments.get(0) as JSONObject)
                        .getJSONObject("photo")
                    imageLink = (jsonObject
                        .getJSONArray("sizes")
                        .get(2) as JSONObject)
                        .getString("url")
                } catch (e: JSONException) {
                }
            }
            val date: String = formatter.format(Date(json.getLong("date") * 1000))
            val likes: Int = json.getJSONObject("likes").getInt("count")
            return News(imageLink, header, text, date, likes)
        }
    }
}