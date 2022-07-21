package com.example.examenIBimestreAlcaciega

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class ModificarPlaneta : AppCompatActivity() {

    val arreglo : ArrayList<Universo> = BaseDatos.ListaPlanetas
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_nombre_planeta)
        val nombreplaneta = intent.getStringExtra("Planeta_nombre")
        val idpro = intent.getIntExtra("Planeta_num",0)
        val idsoact = intent.getIntExtra("PlanetaModificado",0)
        val nombreplanetaactual = findViewById<TextView>(R.id.tv_nombre_planeta_actual)
        nombreplanetaactual.setText(nombreplaneta)
        val btn_salir_Planeta = findViewById<Button>(R.id.btn_salir_planeta)
        btn_salir_Planeta.setOnClickListener{
            abrirActividadParametros(CrearPlanetas::class.java,idsoact)
        }
        val inputNuevoNombre = findViewById<EditText>(R.id.nombre_planeta)
        val btn_actualizar_Planeta = findViewById<Button>(R.id.btn_actualizar_nombre_planeta)
        btn_actualizar_Planeta.setOnClickListener{
            arreglo[idsoact].planetas[idpro]=inputNuevoNombre.text.toString()
            abrirActividadParametros(CrearPlanetas::class.java,idsoact)
        }
    }

    fun abrirDialogo(Titulo:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar",
            null
        )

        val dialog = builder.create()
        dialog.show()
    }

    fun abrirActividadParametros(
        clase:Class<*>,
        id:Int
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("Universo",id+1)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }
}