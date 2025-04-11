package com.example.notesapp.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.database.DataAcessObj
import com.example.notesapp.data.database.DatabaseInstance
import com.example.notesapp.repo.repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun getRoomDatabase(context: Context): DatabaseInstance{
        return Room.databaseBuilder(context, DatabaseInstance::class.java,"NOTES_DATABASE").build()
    }

    @Provides
    @Singleton
    fun getRepo(daao: DataAcessObj):repo{
        return repo(daao)
    }

    @Provides
    @Singleton
    fun getDao(databaseInstance: DatabaseInstance): DataAcessObj{
        return databaseInstance.noteDao()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }



}