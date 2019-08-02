package ru.n00ner.musicradioapp.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.domain.usecase.GetCurrentTrackUseCase
import ru.n00ner.musicradioapp.presentation.model.CurrentTrackUI
import ru.n00ner.musicradioapp.presentation.model.TrackUI
import ru.n00ner.musicradioapp.presentation.viewmodel.mapper.CurrentTrackMapper
import ru.n00ner.musicradioapp.presentation.viewmodel.mapper.TrackHistoryMapper
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class CurrentTrackViewModel(private val getCurrentTrackUseCase: GetCurrentTrackUseCase) : ViewModel() {
    
    private val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val trackOnAirLiveData: MutableLiveData<CurrentTrackUI> = MutableLiveData()
    private val streamUrl: MutableLiveData<String> = MutableLiveData()
    private var timer: CountDownTimer? = null

    val trackOnAir: MutableLiveData<CurrentTrackUI>
        get() = trackOnAirLiveData

    val isError: MutableLiveData<Boolean>
        get() = isErrorLiveData

    val stream: MutableLiveData<String>
        get() = streamUrl

    init {
        handleTracksLoad()
        startTimer()
    }

    public fun handleTracksLoad() {
        viewModelScope.launch {
            updateStreamUrl(getCurrentTrackUseCase.getCurrentStream().data?.stream)
            updateAppropriateLiveData(
                getCurrentTrackUseCase.execute()
            )
        }
    }

    private fun updateAppropriateLiveData(track: Response<CurrentTrack>) {
        if (isResponseSuccess(track)) {
            onResponseSuccess(track.data)
        } else {
            onResponseError()
        }
    }

    private fun updateStreamUrl(url: String?){
        if(!url.isNullOrEmpty()){
            streamUrl.postValue(url)
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

    private fun onDislikeClicked(trackId: Int){

    }
}