package com.davidlopez.contactoscopiar1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.davidlopez.contactoscopiar1.databinding.ActivityMainBinding
import java.util.concurrent.LinkedBlockingQueue

class ContactosActivity : AppCompatActivity(),OnClickListenerContactos,ContactosAux {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: ContactosAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



//NOTAS--------------------------------------------------------------------------------------------
        mBinding.btnSave.setOnClickListener {

            //creamos la nota desde el editText
            val nota=ContactosEntity(name = mBinding.etName.text.toString(), phone = 0, email = "")

//INSERTAR EN BASE DE DATOS-----------------------------------------------------------------------

            //creamos un segundo hilo para la insercion de datos en la base de datos
            Thread {

                //hacemos que la nota creada se inserte en la base de datos
                ContactosApp.db.contactosDao().addNota(nota)
            }.start()
            mAdapter.add(nota)// añadimos la nota con el adaptador
        }

//FRAGMET------------------------------------------------------------------------------------------
        //creamos el componente para añadir contactos desde el fragment
        mBinding.fab.setOnClickListener { launchEditFragment() }//creamos esta funcion en el main
        setupRecyclerView()
    }
    private fun launchEditFragment() {
        // creamos una instancia al fragment
        val fragment=EditContactFragment()
        val fragmentManager =supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain,fragment)
        fragmentTransaction.commit()
        //retroceder al pulsar el boton atras
        fragmentTransaction.addToBackStack(null)

        // ocultamos el boton despues de pulsarlo
        //mBinding.fab.hide()
        hideFab()//este metodo lo oculta y lo vuelve a mostrar al pulsar atras
    }

    private fun setupRecyclerView() {
        mAdapter= ContactosAdapter(mutableListOf(),this)
        mGridLayout= GridLayoutManager(this,2)//numero de elementos por columna
        getNotas()
        mBinding.reciclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=mAdapter
        }
    }

    //funcion para llamar a la base de datos y consultar todas las notas:

    private fun getNotas(){

        //configuramos una cola "queue"para aceptar los tipos de datos
        val queue=LinkedBlockingQueue<MutableList<ContactosEntity>>()

        //abrimos un segundo hilo para que la app no pete.
        Thread {
            val notas = ContactosApp.db.contactosDao().getAllNotas()// consultamos a la base de datos

            // añadimos las consultas a la cola
            queue.add(notas)
        }.start()

        //mostramos los resultados
        mAdapter.setNotas(queue.take())

    }
    /*
    * OnClickListener
    * */
    override fun onClick(contactosEntity: ContactosEntity) {

    }

    override fun onFavoriteContacto(contactosEntity: ContactosEntity) {
        val queue=LinkedBlockingQueue<ContactosEntity>()

        //insertar actualizacion en base de datos   REVISAR
        Thread{
            ContactosApp.db.contactosDao().updateNota(contactosEntity)
            queue.add(contactosEntity)
        }.start()
        mAdapter.update(queue.take())
    }

    //actualizar registro
    fun onFavoriteNota(contactosEntity: ContactosEntity) {

        val queue=LinkedBlockingQueue<ContactosEntity>()

        //insertar actualizacion en base de datos
        Thread{
            ContactosApp.db.contactosDao().updateNota(contactosEntity)
            queue.add(contactosEntity)
        }.start()
        mAdapter.update(queue.take())
    }

    //borrar registro

    override fun onDeleteContacto(notasDB: ContactosEntity) {

        val queue=LinkedBlockingQueue<ContactosEntity>()

        Thread{
            ContactosApp.db.contactosDao().deleteAll(notasDB)
            queue.add(notasDB)
        }.start()
        mAdapter.delete(queue.take())
    }

    /*
    * MainAux
    * */
    override fun hideFab(isVisible: Boolean) {
       if (isVisible)mBinding.fab.show() else mBinding.fab.hide()
    }

    override fun addContact(contactosEntity: ContactosEntity) {
        mAdapter.add(contactosEntity)
    }

    override fun updateContact(contactosEntity: ContactosEntity) {
        // añadir un
    }

}

//nuevo comit