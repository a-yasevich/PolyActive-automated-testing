package com.polyactiveteam.polyactive.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class ImageBitmapString {
    @TypeConverter
    fun bitmapToString(bitmap: Bitmap?): String? {
        if (bitmap == null) {
            return null
        }

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    @TypeConverter
    fun stringToBitmap(encodedString: String?): Bitmap? {
        if (encodedString == null) {
            return null
        }

        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            bitmap
        } catch (e: Exception) {
            e.message
            null
        }
    }
}
