package ru.n00ner.musicradioapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.mobiwise.library.RadioListener
import co.mobiwise.library.RadioManager
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.n00ner.musicradioapp.R
import ru.n00ner.musicradioapp.di.KodeinContainers
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
                    btn_radio.setImageResource(R.drawable.ic_play_arrow)
                    btn_radio.setOnClickListener{
                        radioService.startRadio(streamUrl)
                        Toast.makeText(this@MainActivity,"Буфферизация", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onRadioStarted() {
                runOnUiThread {
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
            CurrentTrackViewModel(instance())
        }
    }

    private fun observerLiveDatas() {
        trackOnAirViewModel.stream.observe(this, Observer(::updateStreamUrl))
        viewModel.tracks.observe(this, Observer(::onTracksReceived))
        viewModel.isError.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyTracks.observe(this, Observer { onEmptyTracksReceived() })
        viewModel.isLoading.observe(this, Observer(::onLoadingStateReceived))
        trackOnAirViewModel.trackOnAir.observe(this, Observer(::updateCurrentTrack))
    }

    private fun onTracksReceived(tracks: List<TrackUI>) {
        showTracks(tracks)
    }

    private fun showTracks(tracksUI: List<TrackUI>?) {
        tracksUI?.let {
            list_track_history.layoutManager = LinearLayoutManager(this)
            val tracksAdapter = TracksAdapter(it.toMutableList(), viewModel, this)
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
        imageView2.setOnClickListener {
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleTracksLoad()
        trackOnAirViewModel.handleTracksLoad()
    }
}
