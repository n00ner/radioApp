package ru.n00ner.musicradioapp.datasource.network

import kotlinx.coroutines.Deferred
import retrofit2.http.*
import ru.n00ner.musicradioapp.datasource.model.CurrentTrackResponse
import ru.n00ner.musicradioapp.utils.dto.SendTrack
import ru.n00ner.musicradioapp.datasource.model.StreamResponse
import ru.n00ner.musicradioapp.datasource.model.TrackResponse

interface RadioApiService {

    @GET("api/v1/stream/android_test/3671/history/")
    fun getTracksHistoryAsync(): Deferred<List<TrackResponse>>?

    @GET("api/v1/stream/android_test/3671/")
    fun getCurrentStreamAsync(): Deferred<StreamResponse>?

    @GET("api/v1/stream/android_test/3671/online_track/")
    fun getCurrentTrack(): Deferred<CurrentTrackResponse>?

    @FormUrlEncoded
    @PUT("api/v1/stream/android_test/3671/dislike_mediafile/")
    fun dislikeTrack(@Field("mediafile") id: Int): Deferred<StreamResponse>?
}