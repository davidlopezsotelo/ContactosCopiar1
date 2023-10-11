package com.davidlopez.contactoscopiar1

import android.app.Application
import androidx.room.Room


class ContactosApp:Application() {
    companion object{
        lateinit var db: NotasDB
    }

    override fun onCreate() {
        super.onCreate()
        db= Room.databaseBuilder(this,NotasDB::class.java,"NotasDataBase").build()//TODO cambiar en destino
    }
}