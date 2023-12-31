package com.davidlopez.contactoscopiar1

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.davidlopez.contactoscopiar1.R
import com.davidlopez.contactoscopiar1.databinding.FragmentEditNotaBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.LinkedBlockingQueue

class EditContactFragment : Fragment() {


    private var mActivity:MainActivity?=null
    private lateinit var mBinding: FragmentEditNotaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding= FragmentEditNotaBinding.inflate(inflater,container,false)

        return mBinding.root
    }
    //creamos el menu------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * para que aparezca el action bar
        * ir a la carpeta res/values/themes/themes.xml
        * y borrar donde pone NoActionBar, en la linea 3
        * */
        mActivity=activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //configuramos el titulo
        mActivity?.supportActionBar?.title=getString(R.string.edit_title_add)//creamos el recurso

        //mostrar menu
        setHasOptionsMenu(true)
    }

    //sobreescribimos los metodos para el menu:
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressedDispatcher?.onBackPressed()//ir hacia atras con el boton??revisar
                true
            }

            // guardar en la base de datos---------------------------------------------------------
            R.id.action_save -> {
                //cambiar entity a contactosEntity*************

                val contacto=NotasEntity(name = mBinding.etName.text.toString().trim())

                val queue =LinkedBlockingQueue<Long?>()
                Thread{
                    hideKeyboard()//para ocultar el teclado
                    val id =NotasApp.db.notasDao().addNota(contacto)// añadir id al contacto para poder ser actualizado
                    queue.add(id)
                }.start()

                //mostrar mensaje---------------------------------
                queue.take()?.let{
                    Snackbar.make(mBinding.root,
                            R.string.edit_message_save_sucess,
                            Snackbar.LENGTH_SHORT)
                             .show()
                    mActivity?.onBackPressedDispatcher?.onBackPressed()

                    // mostrar el nuevo contacto despues de añadirlo, al regresar a la pantalla anterior
                    mActivity?.addContact(contacto)

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        //return super.onOptionsItemSelected(item) , se lo pasamos al else
    }

    //ocultar el teclado------------------------------------------------------------------------
    // no funciona correctamente, mejorar el metodo en toda la app********
    private fun hideKeyboard(){
        val imm=mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager

        imm.hideSoftInputFromWindow(requireView().windowToken,0)
    }

    //ciclo de vida del fragment-----------------------------------------------

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title=getString(R.string.app_name)
        mActivity?.hideFab(true)

        setHasOptionsMenu(false)
        super.onDestroy()
    }
}