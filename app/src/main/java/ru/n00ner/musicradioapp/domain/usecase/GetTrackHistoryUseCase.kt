package ru.n00ner.musicradioapp.domain.usecase


import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GetTrackHistoryUseCase(private val networkRepository: NetworkRepository) {

    suspend fun execute(): Response<List<Track>> {
        val tracks = networkRepository.getTracksHistory()
        var tracksLocal: Response<List<Track>> = tracks

        if (tracks.ResponseType == ResponseType.SUCCESS) {
            tracksLocal = Response.success(convertToLocalTimezone(tracks))
        }
        return tracksLocal
    }

    suspend fun sendDislike(trackId: Int): Response<Stream> {
        return networkRepository.dislikeTrack(trackId)
    }

    private fun convertToLocalTimezone(tracks: Response<List<Track>>): List<Track>? {

        val localTracks: ArrayList<Track>? = ArrayList()

        tracks.data.let {
            it?.forEach { track ->
                if (!track.onAir.isNullOrEmpty()) {

                    val df = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ")
                    var result: Date? = null

                    result = df.parse(track.onAir)
                    println("date:$result") //prints date in current locale
                    val sdf = SimpleDateFormat("HH:mm")
                    sdf.setTimeZone(TimeZone.getDefault())
                    localTracks?.add(
                        Track(
                            track.id,
                            track.title,
                            track.performerTitle,
                            sdf.format(result),
                            track.mediafile
                        )
                    )
                }
            }

        }
        return localTracks
    }
}