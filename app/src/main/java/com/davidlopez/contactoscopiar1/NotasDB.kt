package com.davidlopez.contactoscopiar1

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(NotasEntity::class), version = 1, exportSchema = false)
abstract class NotasDB :RoomDatabase(){
    abstract fun notasDao():NotasDao
}