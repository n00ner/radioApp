package ru.n00ner.musicradioapp.domain.usecase

import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.presentation.AppPreferences
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class SendDislikeUseCase (private val networkRepository: NetworkRepository, private val prefs: AppPreferences) {

    suspend fun execute(trackId: Int): Response<Stream> {
        prefs.deletedTracks?.forEach {
            if(it == trackId){
                return Response.success(null)
            }
        }
        val stream = networkRepository.dislikeTrack(trackId)
        if(stream.ResponseType == ResponseType.SUCCESS){
            prefs.addDeletedTrack(trackId)
            return Response.success(Stream(stream.data!!.id, stream.data.title,"https://f.muz-lab.ru/" + stream.data.image, stream.data.stream))
        }
        return Response.error("Stream is null")
    }
}