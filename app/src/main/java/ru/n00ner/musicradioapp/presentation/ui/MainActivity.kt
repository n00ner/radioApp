package ru.n00ner.musicradioapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.mobiwise.library.RadioListener
import co.mobiwise.library.RadioManager
import com.bumptech.glide.Glide
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.n00ner.musicradioapp.R
import ru.n00ner.musicradioapp.di.KodeinContainers
import ru.n00ner.musicradioapp.domain.usecase.SendDislikeUseCase
import ru.n00ner.musicradioapp.presentation.AppPreferences
import ru.n00ner.musicradioapp.presentation.model.CurrentTrackUI
import ru.n00ner.musicradioapp.presentation.model.TrackUI
import ru.n00ner.musicradioapp.presentation.ui.adapters.TracksAdapter
import ru.n00ner.musicradioapp.presentation.viewmodel.CurrentTrackViewModel
import ru.n00ner.musicradioapp.presentation.viewmodel.TrackHistoryViewModel

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: TrackHistoryViewModel
    private lateinit var trackOnAirViewModel: CurrentTrackViewModel
    private var streamUrl = ""
    var radioService = RadioManager.with(this)

    override fun onStart() {
        super.onStart()
        radioService.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        radioService.disconnect()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        observerLiveDatas()

        radioService.enableNotification(true)
        radioService.registerListener(object: RadioListener{
            override fun onRadioConnected() {

            }

            override fun onRadioStopped() {
                runOnUiThread {
                    img_eq.setImageResource(R.drawable.ic_equalizer)
                    btn_radio.setImageResource(R.drawable.ic_play_arrow)
                    btn_radio.setOnClickListener{
                        radioService.startRadio(streamUrl)
                        Toast.makeText(this@MainActivity,"Буфферизация", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onRadioStarted() {
                runOnUiThread {
                    img_eq.setImageResource(R.drawable.ic_active_equalizer)
                    btn_radio.setImageResource(R.drawable.ic_pause)
                    btn_radio.setOnClickListener{
                        radioService.stopRadio()
                    }
                }
            }

            override fun onMetaDataReceived(p0: String?, p1: String?) {

            }

        })
    }

    private fun initViewModel() {
        viewModel = KodeinContainers.diBaseProject.newInstance {
            TrackHistoryViewModel(instance())
        }
        trackOnAirViewModel = KodeinContainers.diBaseProject.newInstance {
            CurrentTrackViewModel(instance(), instance(), instance())
        }
    }

    private fun observerLiveDatas() {
        trackOnAirViewModel.stream.observe(this, Observer(::updateStreamUrl))
        viewModel.tracks.observe(this, Observer(::onTracksReceived))
        viewModel.isError.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyTracks.observe(this, Observer { onEmptyTracksReceived() })
        viewModel.isLoading.observe(this, Observer(::onLoadingStateReceived))
        trackOnAirViewModel.trackOnAir.observe(this, Observer(::updateCurrentTrack))
        trackOnAirViewModel.title.observe(this,Observer(::updateTitle))
        trackOnAirViewModel.poster.observe(this, Observer(::updatePoster))
        trackOnAirViewModel.isDisliked.observe(this,Observer(::updateCurrentTrackDislikeBtn) )
    }

    private fun onTracksReceived(tracks: List<TrackUI>) {
        showTracks(tracks)
    }

    private fun showTracks(tracksUI: List<TrackUI>?) {
        val prefs = KodeinContainers.diBaseProject.newInstance {
            AppPreferences(instance())
        }
        val dislikeUseCase = KodeinContainers.diBaseProject.newInstance {
            SendDislikeUseCase(instance(), instance())
        }
        tracksUI?.let {
            list_track_history.layoutManager = LinearLayoutManager(this)
            val tracksAdapter = TracksAdapter(it.toMutableList(),prefs, dislikeUseCase )
            list_track_history.adapter = tracksAdapter
            tracksAdapter.updateAdapter(it.toMutableList())
            list_track_history.setHasFixedSize(true)
        }
    }

    private fun onErrorReceived() {

    }

    private fun onEmptyTracksReceived() {

    }

    private fun updateStreamUrl(url: String){
        streamUrl = url
        btn_radio.setImageResource(R.drawable.ic_play_arrow)
        btn_radio.setOnClickListener {
            radioService.startRadio(streamUrl)
        }
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        progressBar.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun updateCurrentTrack(track: CurrentTrackUI){
        tv_track_title.text = track.title
        tv_track_performer.text = track.performerTitle
        btn_cur_track_dislike.setOnClickListener {
            trackOnAirViewModel.handleCurrentTrackDislike(track.id)
        }
    }

    private fun updatePoster(url: String?){
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.photo)
            .override(200, 300)
            .into(stream_poster)
    }

    private fun updateCurrentTrackDislikeBtn(isUnlike: Boolean){
        if(isUnlike){
            btn_cur_track_dislike.alpha = 1.0f
            btn_cur_track_dislike.setOnClickListener(null)
            Toast.makeText(this, "Трек отмечен как нежелательный!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Не удалось отправить запрос!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTitle(title: String?){
        if(!title.isNullOrEmpty())
            stream_title.text = title
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleTracksLoad()
        trackOnAirViewModel.handleTracksLoad()
    }
}
