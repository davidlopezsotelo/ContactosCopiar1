package com.davidlopez.contactoscopiar1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidlopez.contactoscopiar1.databinding.ItemStoreBinding

class ContactosAdapter(private var notas:MutableList<ContactosEntity>, private var listener: OnClickListenerContactos):
    RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {

    private lateinit var mContex:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       mContex=parent.context

        val view =LayoutInflater.from(mContex).inflate(R.layout.item_store,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = notas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notas=notas.get(position)

        with(holder){
            setListener(notas)
            binding.tvName.text=notas.name
            //binding.cbNotas.isChecked=notas.isFaborite
        }

    }

    fun add(nota: ContactosEntity) {    //REPARAR O MEJORAR??????
        if (!notas.contains(nota))
        {notas.add(nota)
        notifyItemInserted(notas.size-1)}
    }

    fun setNotas(notas: MutableList<ContactosEntity>) {
        this.notas=notas
        notifyDataSetChanged()
    }

    fun update(contactosEntity: ContactosEntity) {
        val index=notas.indexOf(contactosEntity)
        if (index!=-1){
            notas.set(index,contactosEntity)
            notifyItemChanged(index)
        }
    }

    fun delete(contactosEntity: ContactosEntity) {
        val index=notas.indexOf(contactosEntity)
        if (index!=-1){
            notas.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding=ItemStoreBinding.bind(view)

        fun setListener(contactosEntity: ContactosEntity){

            with(binding.root) {
                setOnClickListener { listener.onClick(contactosEntity) }

                //TODO CAMBIAR PARA MENU TOAST CON OPCION BORRAR O ENVIAR
                setOnLongClickListener {
                    listener.onDeleteContacto(contactosEntity)
                    true
                }
            }

            //binding.cbNotas.setOnClickListener { listener.onFavoriteNota(contactosEntity) }



        }

    }
}