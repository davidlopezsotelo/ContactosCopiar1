package com.davidlopez.contactoscopiar1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ContactosDao {
    @Query("SELECT * FROM ContactosEntity")
    fun getAllNotas():MutableList<ContactosEntity>

    // para contactosEntity**********************************
    @Query("SELECT * FROM ContactosEntity where id= :id")
    fun getContactoById(id: Long):ContactosEntity

    @Insert
    fun addNota(contactosEntity: ContactosEntity):Long // contactosEntity :Long

    @Update
    fun updateNota(contactosEntity: ContactosEntity)

    @Delete
    fun deleteAll(contactosEntity: ContactosEntity)
}