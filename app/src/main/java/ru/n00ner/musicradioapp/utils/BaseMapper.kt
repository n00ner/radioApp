package ru.n00ner.musicradioapp.utils

interface BaseMapper<in A, out B> {

    fun mapFrom(type: A?): B
}