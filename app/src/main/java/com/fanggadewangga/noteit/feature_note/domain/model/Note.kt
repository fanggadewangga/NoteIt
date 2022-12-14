package com.fanggadewangga.noteit.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fanggadewangga.noteit.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, LightBlue, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)
