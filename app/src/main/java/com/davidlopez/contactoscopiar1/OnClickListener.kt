package com.davidlopez.contactoscopiar1



interface OnClickListener {
    fun onClick(notasEntity: NotasEntity)

    //actualizar registro
    fun onFavoriteNota(notasEntity: NotasEntity)

    //borrar registri
    fun onDeleteNota(notasDB: NotasEntity)
}