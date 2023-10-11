package com.davidlopez.contactoscopiar1



interface OnClickListenerContactos {
    fun onClick(contactosEntity: ContactosEntity)

    //actualizar registro
    fun onFavoriteContacto(contactosEntity: ContactosEntity)

    //borrar registri

    fun onDeleteContacto(notasDB: ContactosEntity)
}