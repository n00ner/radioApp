package ru.n00ner.musicradioapp.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.domain.usecase.GetTrackHistoryUseCase
import ru.n00ner.musicradioapp.presentation.model.TrackUI
import ru.n00ner.musicradioapp.presentation.viewmodel.mapper.TrackHistoryMapper
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class TrackHistoryViewModel(private val getTrackHistoryUseCase: GetTrackHistoryUseCase) : ViewModel() {

    private val tracksLiveData: MutableLiveData<List<TrackUI>> = MutableLiveData()
    private val areEmptyTracksLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var timer: CountDownTimer? = null

    val tracks: MutableLiveData<List<TrackUI>>
        get() = tracksLiveData

    val areEmptyTracks: MutableLiveData<Boolean>
        get() = areEmptyTracksLiveData

    val isError: MutableLiveData<Boolean>
        get() = isErrorLiveData

    val isLoading: MutableLiveData<Boolean>
        get() = isLoadingLiveData

    init {
        handleTracksLoad()
        startTimer()
    }

    public fun handleTracksLoad() {
        viewModelScope.launch {
            isLoadingLiveData(true)
            updateAppropriateLiveData(
                getTrackHistoryUseCase.execute()
            )
        }
    }

    private fun updateAppropriateLiveData(Response: Response<List<Track>>) {
        if (isResponseSuccess(Response)) {
            onResponseSuccess(Response.data)
        } else {
            onResponseError()
        }

        isLoadingLiveData(false)
    }

    private fun isResponseSuccess(Response: Response<List<Track>>) =
        Response.ResponseType == ResponseType.SUCCESS

    private fun onResponseSuccess(tracksModel: List<Track>?) {
        val tracks = TrackHistoryMapper.mapFrom(tracksModel)

        if (tracks.isEmpty()) {
            areEmptyTracksLiveData.postValue(true)
        } else {
            tracksLiveData.postValue(tracks)
        }
    }

    private fun onResponseError() {
        isErrorLiveData.postValue(true)
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingLiveData.postValue(isLoading)
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

    public fun onDislikeClicked(trackId: Int){

        viewModelScope.launch {
                getTrackHistoryUseCase.sendDislike(trackId)
        }

    }
}
