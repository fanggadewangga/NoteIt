package com.fanggadewangga.noteit.di

import android.app.Application
import androidx.room.Room
import com.fanggadewangga.noteit.feature_note.data.data_source.NoteDatabase
import com.fanggadewangga.noteit.feature_note.data.repository.NoteRepositoryImpl
import com.fanggadewangga.noteit.feature_note.domain.repository.NoteRepository
import com.fanggadewangga.noteit.feature_note.domain.use_case.*
import com.fanggadewangga.noteit.feature_note.presentation.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NoteDatabase{
        // inMemoryDatabase = write data to memory without persistence storage
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases (
            addNotes = AddNotesUseCase(repository),
            getNoteUseCases = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            getANote = GetANoteUseCase(repository)
        )
    }
}