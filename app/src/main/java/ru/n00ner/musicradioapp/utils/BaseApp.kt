package ru.n00ner.musicradioapp.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class BaseApp : Application() {

    init{
        instance = this
    }

    companion object {
        private var instance: BaseApp? = null

        fun applicationContext(): Context{
            return instance!!.applicationContext
        }
    }
}