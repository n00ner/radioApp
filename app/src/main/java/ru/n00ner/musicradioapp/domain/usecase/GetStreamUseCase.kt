package ru.n00ner.musicradioapp.domain.usecase

import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class GetStreamUseCase (private val networkRepository: NetworkRepository) {

    suspend fun execute(): Response<Stream> {
        val stream = networkRepository.getStream()
        if(stream.ResponseType == ResponseType.SUCCESS){
            return Response.success(Stream(stream.data!!.id, stream.data.title,"https://f.muz-lab.ru/" + stream.data.image, stream.data.stream))
        }
        return Response.error("Stream is null")
    }

    suspend fun sendDislike(trackId: Int): Response<Stream> {
        return networkRepository.dislikeTrack(trackId)
    }
}