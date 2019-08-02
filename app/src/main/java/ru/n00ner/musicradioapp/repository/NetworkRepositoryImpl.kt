package ru.n00ner.musicradioapp.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.n00ner.musicradioapp.datasource.RadioNetworkDatasource
import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.repository.mapper.CurrentTrackMapper
import ru.n00ner.musicradioapp.repository.mapper.StreamMapper
import ru.n00ner.musicradioapp.repository.mapper.TrackHistoryMapper
import ru.n00ner.musicradioapp.utils.dto.Response

@ExperimentalCoroutinesApi
class NetworkRepositoryImpl constructor(private val radioNetworkDatasource: RadioNetworkDatasource) :
    NetworkRepository {
    override suspend fun getTracksHistory(): Response<List<Track>> {
        return TrackHistoryMapper.mapFrom(radioNetworkDatasource.getTrackHistory())
    }

    override suspend fun getCurrentTrack(): Response<CurrentTrack> {
        return CurrentTrackMapper.mapFrom(radioNetworkDatasource.getCurrentTrack())
    }

    override suspend fun getStream(): Response<Stream> {
        return StreamMapper.mapFrom(radioNetworkDatasource.getCurrentStream())
    }

    override suspend fun dislikeTrack(trackId: Int): Response<Stream> {
        return StreamMapper.mapFrom(radioNetworkDatasource.dislikeTrack(trackId))
    }

}
