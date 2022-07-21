package com.example.examenIBimestreAlcaciega

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts


class CrearPlanetas : AppCompatActivity() {
    val arreglo : ArrayList<Universo> = BaseDatos.ListaPlanetas
    var idItemSeleccionadoPlaneta = 0
    var id=0;
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){

            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_planetas)
        val idcreado = intent.getIntExtra("Universo",0)
        id=idcreado
        val listView = findViewById<ListView>(R.id.lv_planetas)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo[idcreado-1].planetas
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btn_saliraMain = findViewById<Button>(R.id.btn_salir_aniadir_prog)
        btn_saliraMain.setOnClickListener{
            irActividad(MainActivity::class.java)
        }

        val btn_aniadirProg = findViewById<Button>(R.id.btn_aniadir_programa)
        btn_aniadirProg.setOnClickListener{
            anadirPrograma(adaptador,idcreado-1)
            val cajaNombrePrograma=findViewById<EditText>(R.id.input_planetas)
            cajaNombrePrograma.setText("")
        }
        registerForContextMenu(listView)
    }


    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun anadirPrograma(
        adaptador: ArrayAdapter<String>,
        index: Int
    ){
        val cajaNombrePrograma=findViewById<EditText>(R.id.input_planetas)
        val nombrePrograma=cajaNombrePrograma.text.toString()
        arreglo[index].planetas.add(
            nombrePrograma
        )
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menuprogramas,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionadoPlaneta=id
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_Pro->{
                abrirActividadParametros(ModificarPlaneta::class.java,idItemSeleccionadoPlaneta,id-1)
                "${idItemSeleccionadoPlaneta}"
                return true
            }
            R.id.mi_eliminar_Planeta->{
                arreglo[id-1].planetas.removeAt("${idItemSeleccionadoPlaneta}".toString().toInt())
                "${idItemSeleccionadoPlaneta}"
                val listView = findViewById<ListView>(R.id.lv_planetas)
                val adaptador = ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_list_item_1,//Como se va a ver el xml
                    arreglo[id-1].planetas
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun abrirActividadParametros(
        clase:Class<*>,
        idProg:Int,
        idSOAct:Int
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("Universo_nombre", arreglo[id-1].planetas["${idItemSeleccionadoPlaneta}".toString().toInt()])
        intentExplicito.putExtra("Universo_num",idProg)
        intentExplicito.putExtra("UniversoModificado",idSOAct)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }

}
