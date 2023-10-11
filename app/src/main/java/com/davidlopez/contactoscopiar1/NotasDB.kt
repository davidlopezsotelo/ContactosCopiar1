package com.davidlopez.contactoscopiar1

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(ContactosEntity::class), version = 1, exportSchema = false)
abstract class NotasDB :RoomDatabase(){
    abstract fun contactosDao():ContactosDao
}