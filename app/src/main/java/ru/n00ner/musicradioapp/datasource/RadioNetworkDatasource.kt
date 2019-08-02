package ru.n00ner.musicradioapp.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import ru.n00ner.musicradioapp.datasource.model.CurrentTrackResponse
import ru.n00ner.musicradioapp.utils.dto.SendTrack
import ru.n00ner.musicradioapp.datasource.model.StreamResponse
import ru.n00ner.musicradioapp.datasource.model.TrackResponse
import ru.n00ner.musicradioapp.datasource.network.RadioApiClient
import ru.n00ner.musicradioapp.datasource.network.RadioApiService
import ru.n00ner.musicradioapp.utils.NETWORK_DATASOURCE_ERROR_MESSAGE
import ru.n00ner.musicradioapp.utils.dto.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
class RadioNetworkDatasource(private val radioClient: RadioApiClient) {

    suspend fun getTrackHistory(): Response<List<TrackResponse>>? {
        var result: Response<List<TrackResponse>> = Response.success(listOf())

        withContext(Dispatchers.IO) {
            try {
                val radioService = radioClient.getRetrofitInstance().create(RadioApiService::class.java)
                val request = radioService.getTracksHistoryAsync()
                val trackList = request?.await()
                request?.let {
                    if (it.isCompleted)
                        result = Response.success(trackList)
                    else if (it.isCancelled)
                        result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
                }
            } catch (exp: Exception) {
                result = Response.error(exp.localizedMessage)
            }
        }
        return result
    }

    suspend fun getCurrentStream(): Response<StreamResponse> {
        var result: Response<StreamResponse> = Response.success(null)

        withContext(Dispatchers.IO){
            try{
                val radioService = radioClient.getRetrofitInstance().create(RadioApiService::class.java)
                val request = radioService.getCurrentStreamAsync()
                val stream = request?.await()
                request?.let {
                    if(it.isCompleted)
                        result = Response.success(stream)
                    else if(it.isCancelled)
                        result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
                }
            }catch (exp: Exception){
                result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
            }
        }
        return result
    }

    suspend fun getCurrentTrack(): Response<CurrentTrackResponse>{
        var result: Response<CurrentTrackResponse> = Response.success(null)

        withContext(Dispatchers.IO){
            try {
                val radioService = radioClient.getRetrofitInstance().create(RadioApiService::class.java)
                val request = radioService.getCurrentTrack()
                val currentTrack = request?.await()
                request?.let {
                    if(it.isCompleted)
                        result = Response.success(currentTrack)
                    else if(it.isCancelled)
                        result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
                }
            }catch (exp: Exception){
                result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
            }
        }
        return result
    }

    suspend fun dislikeTrack(trackId: Int): Response<StreamResponse>{
        var result: Response<StreamResponse> = Response.success(null)

        withContext(Dispatchers.IO){
            try{
                val radioService = radioClient.getRetrofitInstance().create(RadioApiService::class.java)
                val request = radioService.dislikeTrack(trackId)
                val stream = request?.await()
                request?.let {
                    if(it.isCompleted)
                        result = Response.success(stream)
                    else if(it.isCancelled)
                        result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
                }
            }catch (exp: Exception){
                result = Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
            }
        }
        return result
    }
}