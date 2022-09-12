package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ACrearNovela : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acrear_novela)

        val botonCrearNovela = findViewById<Button>(R.id.btn_crear_novela)
        botonCrearNovela
            .setOnClickListener {
                crearNovela()
                openActivity( ANovelas::class.java )
            }

    }

    fun openActivity(
        clase: Class<*>,
    ) {
        val intent = Intent( this,clase )
        startActivity( intent )
    }

    fun crearNovela() {
        val editTextNombre = findViewById<EditText>(R.id.et_titulo_novela)
        val editTextAtividad = findViewById<EditText>(R.id.et_descripcion_novela)
        val editTextPromedioGPP = findViewById<EditText>(R.id.et_likes_novela)
        val nuevaNovela = hashMapOf<String, Any>(
            "titulo" to editTextNombre.text.toString(),
            "descripcion" to editTextAtividad.text.toString(),
            "likes" to editTextPromedioGPP.text.toString().toInt()
        )
        val db = Firebase.firestore
        val referencia = db.collection("novelas")
        referencia
            .add(nuevaNovela)
            .addOnSuccessListener {
                editTextNombre.text.clear()
                editTextAtividad.text.clear()
                editTextPromedioGPP.text.clear()
            }
            .addOnFailureListener { }
    }

}