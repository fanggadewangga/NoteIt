package com.fanggadewangga.noteit.feature_note.domain.use_case

import com.fanggadewangga.noteit.feature_note.domain.model.Note
import com.fanggadewangga.noteit.feature_note.domain.repository.NoteRepository

class GetANoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}