package ru.n00ner.musicradioapp.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.domain.usecase.GetCurrentTrackUseCase
import ru.n00ner.musicradioapp.domain.usecase.GetStreamUseCase
import ru.n00ner.musicradioapp.domain.usecase.SendDislikeUseCase
import ru.n00ner.musicradioapp.presentation.model.CurrentTrackUI
import ru.n00ner.musicradioapp.presentation.viewmodel.mapper.CurrentTrackMapper
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class CurrentTrackViewModel(private val getCurrentTrackUseCase: GetCurrentTrackUseCase, private val getStreamUseCase: GetStreamUseCase, private val sendDislikeUseCase: SendDislikeUseCase) : ViewModel() {
    
    private val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val trackOnAirLiveData: MutableLiveData<CurrentTrackUI> = MutableLiveData()
    private val streamUrl: MutableLiveData<String> = MutableLiveData()
    private val streamTitle: MutableLiveData<String> = MutableLiveData()
    private val streamPoster: MutableLiveData<String> = MutableLiveData()
    private val dislike: MutableLiveData<Boolean> = MutableLiveData()


    private var timer: CountDownTimer? = null

    val trackOnAir: MutableLiveData<CurrentTrackUI>
        get() = trackOnAirLiveData

    val isError: MutableLiveData<Boolean>
        get() = isErrorLiveData

    val isDisliked: MutableLiveData<Boolean>
        get() = dislike

    val stream: MutableLiveData<String>
        get() = streamUrl

    val poster: MutableLiveData<String>
        get() = streamPoster

    val title: MutableLiveData<String>
        get() = streamTitle

    init {
        handleTracksLoad()
        startTimer()
    }

    fun handleTracksLoad() {
        viewModelScope.launch {
            updateStream(getStreamUseCase.execute().data)
            updateAppropriateLiveData(
                getCurrentTrackUseCase.execute()
            )
        }
    }

    fun handleCurrentTrackDislike(trackId: Int){
        viewModelScope.launch {
            if(sendDislikeUseCase.execute(trackId).ResponseType != ResponseType.ERROR)
                isDisliked.postValue(true)
            else
                isDisliked.postValue(false)
        }
    }

    private fun updateAppropriateLiveData(track: Response<CurrentTrack>) {
        if (isResponseSuccess(track)) {
            onResponseSuccess(track.data)
        } else {
            onResponseError()
        }
    }

    private fun updateStream(stream: Stream?){
        if(stream != null){
            streamUrl.postValue(stream.stream)
            poster.postValue(stream.image)
            title.postValue(stream.title)
        }
    }

    private fun isResponseSuccess(Response: Response<CurrentTrack>) =
        Response.ResponseType == ResponseType.SUCCESS

    private fun onResponseSuccess(track: CurrentTrack?) {
        val trackUi = CurrentTrackMapper.mapFrom(track)

        if (trackUi != null) {
            trackOnAir.postValue(trackUi)
        } else {
            trackOnAir.postValue(CurrentTrackUI(-1, "Не удалось загрузить", "Проверьте настройки интернета"))
        }
    }

    private fun onResponseError() {
        isErrorLiveData.postValue(true)
    }
    private fun startTimer(){
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                handleTracksLoad()
            }
        }
        timer?.start()
    }

    private fun stopTimer(){
        if(timer != null)
            timer?.cancel()
    }
}