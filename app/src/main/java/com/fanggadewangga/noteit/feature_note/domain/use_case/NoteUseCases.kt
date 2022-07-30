package com.fanggadewangga.noteit.feature_note.domain.use_case

data class NoteUseCases(
    val addNotes: AddNotesUseCase,
    val getNoteUseCases: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)