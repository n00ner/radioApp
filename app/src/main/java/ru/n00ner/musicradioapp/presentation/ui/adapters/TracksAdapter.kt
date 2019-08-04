package ru.n00ner.musicradioapp.presentation.ui.adapters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_track.view.*
import ru.n00ner.musicradioapp.R
import ru.n00ner.musicradioapp.presentation.AppPreferences
import ru.n00ner.musicradioapp.presentation.model.TrackUI
import ru.n00ner.musicradioapp.presentation.viewmodel.TrackHistoryViewModel

class TracksAdapter(private var tracks: MutableList<TrackUI>, private var viewmodel: TrackHistoryViewModel, private var context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        )


    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            trackTitleTextView.text = tracks[position].title
            trackOnAirTextView.text = tracks[position].onAir
            trackPerformerTextView.text = tracks[position].performerTitle
            trackDislikeButton.setOnClickListener {
                viewmodel.onDislikeClicked(tracks[position].mediafile)
                Toast.makeText(trackDislikeButton.context, "Трек отмечен как нежелательный", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun removeAt(position: Int){
        tracks.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, tracks.size)
    }


    fun updateAdapter(updatedList: List<TrackUI>) {
        val result = DiffUtil.calculateDiff(TracksDiffCallback(this.tracks, updatedList))
        this.tracks = updatedList.toMutableList()
        result.dispatchUpdatesTo(this)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val trackTitleTextView: TextView = view.tv_track_title
    val trackPerformerTextView: TextView = view.tv_track_performer
    val trackOnAirTextView: TextView = view.tv_track_on_air
    val trackDislikeButton: ImageView = view.btn_track_dislike
}