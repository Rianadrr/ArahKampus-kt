package com.example.arah_kampus.data.local

import android.content.Context

object Prefs {
    private const val PREF_NAME = "campus_prefs"
    private const val KEY_FAVORITE = "FAVORITE_BUILDING"
    private const val KEY_LAST_LAT = "LAST_LAT"
    private const val KEY_LAST_LNG = "LAST_LNG"

    fun saveFavorite(context: Context, name: String, lat: Double, lng: Double) {
        val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sp.edit()
            .putString(KEY_FAVORITE, name)
            .putFloat(KEY_LAST_LAT, lat.toFloat())
            .putFloat(KEY_LAST_LNG, lng.toFloat())
            .apply()
    }

    fun getFavorite(context: Context): Triple<String?, Double?, Double?> {
        val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val name = sp.getString(KEY_FAVORITE, null)
        val lat = if (sp.contains(KEY_LAST_LAT)) sp.getFloat(KEY_LAST_LAT, 0f).toDouble() else null
        val lng = if (sp.contains(KEY_LAST_LNG)) sp.getFloat(KEY_LAST_LNG, 0f).toDouble() else null
        return Triple(name, lat, lng)
    }
}
