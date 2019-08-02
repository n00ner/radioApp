package ru.n00ner.musicradioapp.domain


import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.utils.dto.Response

interface NetworkRepository {
    suspend fun getTracksHistory(): Response<List<Track>>
    suspend fun getCurrentTrack(): Response<CurrentTrack>
    suspend fun getStream(): Response<Stream>
    suspend fun dislikeTrack(trackId: Int): Response<Stream>
}