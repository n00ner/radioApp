package ru.n00ner.musicradioapp.presentation

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPreferences(context: Context) {
    private val APP_PREFERENCES = context.packageName
   private val DELETED_TRACKS = "deleted_tracks"



    val preferences: SharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    var deletedTracks: ArrayList<Int>?
        get() = Gson().fromJson(preferences.getString(DELETED_TRACKS, "[]"), object : TypeToken<ArrayList<Int>?>() {}.type)
        set(value) = preferences.edit().putString(DELETED_TRACKS, Gson().toJson(value)).apply()









}