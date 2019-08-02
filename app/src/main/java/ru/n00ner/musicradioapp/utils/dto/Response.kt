package ru.n00ner.musicradioapp.utils.dto

data class Response<out T>(
    var ResponseType: ResponseType,
    val data: T? = null,
    val message: String? = null
) {

    companion object {
        fun <T> success(data: T?): Response<T> {
            return Response(ResponseType.SUCCESS, data)
        }

        fun <T> error(message: String): Response<T> {
            return Response(
                ResponseType.ERROR,
                message = message
            )
        }
    }
}

enum class ResponseType {
    ERROR,
    LOADING,
    SUCCESS,
    EMPTY_DATA
}