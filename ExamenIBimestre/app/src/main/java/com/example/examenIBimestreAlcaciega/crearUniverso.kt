package com.example.examenIBimestreAlcaciega

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class crearUniverso : AppCompatActivity() {

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
        setContentView(R.layout.activity_crear_universo)

        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )

        val botonAnadirListUniverso = findViewById<Button>(
            R.id.btn_crear_universo
        )
        botonAnadirListUniverso.setOnClickListener {
            anadirNumero(adaptador)
            abrirDialogo("Creado!")
            var cajaNombreUniverso=findViewById<EditText>(R.id.txt_input_universo)
            cajaNombreUniverso.setText("")
        }
        val botonIrASO = findViewById<Button>(R.id.btn_iraverso)
        botonIrASO.setOnClickListener{
            irActividad(MainActivity::class.java)
        }
        val botonIrPrgroamas = findViewById<Button>(R.id.btn_iraniadir_prog)
        botonIrPrgroamas.setOnClickListener{
            if(arreglo.size>=1) {
                abrirActividadParametros(CrearPlanetas::class.java)
            }else{
                abrirDialogo("Lista Vacia!")
            }
        }
    }

    fun anadirNumero(
        adaptador: ArrayAdapter<Universo>
    ){
        var cajaNombreSO=findViewById<EditText>(R.id.txt_input_universo)
        var nombreSO=cajaNombreSO.text.toString()
        arreglo.add(
            Universo(nombreSO)
        )
        adaptador.notifyDataSetChanged()
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

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("Universo",arreglo.size)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }
}