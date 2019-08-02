package ru.n00ner.musicradioapp.presentation.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.n00ner.musicradioapp.presentation.model.TrackUI

class TracksDiffCallback(private val oldTracks: List<TrackUI>,
                        private val newTracks: List<TrackUI>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTracks[oldItemPosition].id == newTracks[newItemPosition].id

    override fun getOldListSize(): Int = oldTracks.size

    override fun getNewListSize(): Int = newTracks.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTracks[oldItemPosition] == newTracks[newItemPosition]
}