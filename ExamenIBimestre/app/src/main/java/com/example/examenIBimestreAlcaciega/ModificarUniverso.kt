package com.example.examenIBimestreAlcaciega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ModificarUniverso : AppCompatActivity() {
    val arreglo : ArrayList<Universo> = BaseDatos.ListaPlanetas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_nombre_universo)
        val nombre = intent.getStringExtra("nombreUniverso")
        val idSOE = intent.getIntExtra("Universo_num", 0)
        val nombreActual = findViewById<TextView>(R.id.tv_nombre_actual_universo)
        nombreActual.setText(nombre)
        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_editar_nombre_universo)
        val btnActualizarNombreUniverso = findViewById<Button>(R.id.btn_actualizar_nombre_universo)
        val inputNombreUniverso = findViewById<EditText>(R.id.modificar_nombre_universo)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        btnCancelar.setOnClickListener{
            inputNombreUniverso.setText("")
            irActividad(MainActivity::class.java)
        }
        btnActualizarNombreUniverso.setOnClickListener{
            arreglo[idSOE].nombre=inputNombreUniverso.text.toString()
            adaptador.notifyDataSetChanged()
            irActividad(MainActivity::class.java)
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}