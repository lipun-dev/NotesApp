package com.example.notesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.data.entity.Notes


@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class DatabaseInstance: RoomDatabase() {
    abstract fun noteDao(): DataAcessObj
}