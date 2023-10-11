package  com.davidlopez.contactoscopiar1
interface ContactosAux {
    fun hideFab(isVisible:Boolean=false)
    // actualizar la vista desde el fragment
    fun addContact(contactosEntity: ContactosEntity)
    fun updateContact(contactosEntity: ContactosEntity)
}