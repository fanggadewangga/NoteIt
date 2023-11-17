package com.fanggadewangga.noteit.feature_note.domain.use_case

import com.fanggadewangga.noteit.feature_note.domain.model.InvalidNoteException
import com.fanggadewangga.noteit.feature_note.domain.model.Note
import com.fanggadewangga.noteit.feature_note.domain.use_case.data.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddNotesUseCaseTest {
    private lateinit var addNotesUseCase: AddNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNotesUseCase = AddNotesUseCase(fakeNoteRepository)
    }

    @Test
    fun `insert note by given a valid note, note inserted`() = runBlocking {
        // Given a valid note
        val validNote = Note(
            title = "Valid Title",
            content = "Valid Content",
            timestamp = 1L,
            color = 1,
            id = Math.random().toInt()
        )

        // When the use case is invoked
        addNotesUseCase.invoke(validNote)

        // Then the note should be inserted into the repository
        val savedNote = fakeNoteRepository.getNoteById(validNote.id!!)
        assertEquals(validNote, savedNote)
    }

    @Test(expected = InvalidNoteException::class)
    fun `insert note by given invalid title, throw InvalidNoteException`() = runBlocking {
        // Given a note with an empty title
        val invalidNote = Note(
            title = "",
            content = "Valid Content",
            timestamp = 1L,
            color = 1,
            id = Math.random().toInt()
        )

        // When the use case is invoked, it should throw InvalidNoteException
        addNotesUseCase.invoke(invalidNote)
    }

    @Test(expected = InvalidNoteException::class)
    fun `insert note by given invalid content, throw InvalidNoteException`() = runBlocking {
        // Given a note with empty content
        val invalidNote = Note(
            title = "Valid Title",
            content = "",
            timestamp = 1L,
            color = 1,
            id = Math.random().toInt()
        )

        // When the use case is invoked, it should throw InvalidNoteException
        addNotesUseCase.invoke(invalidNote)
    }
}