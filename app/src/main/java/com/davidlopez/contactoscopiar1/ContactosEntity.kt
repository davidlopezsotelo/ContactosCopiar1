package com.davidlopez.contactoscopiar1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ContactosEntity")
data class ContactosEntity(@PrimaryKey (autoGenerate = true)
                           var id:Long=0,
                           var name:String,
                           var email:String,
                           var phone:Int)
