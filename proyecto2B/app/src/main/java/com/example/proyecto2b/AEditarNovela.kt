package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AEditarNovela : AppCompatActivity() {

    var idNovela = "";

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeditar_novela)

        idNovela = intent.getStringExtra("id").toString()

        val tvTitulo = findViewById<TextView>(R.id.tv_titulo_novela)
        val etDescripcion = findViewById<EditText>(R.id.et_editar_descripcion)
        val etLikes = findViewById<EditText>(R.id.et_editar_likes)

        val db = Firebase.firestore
        val novelasRef = db
            .collection("novelas")
        novelasRef
            .document(idNovela)
            .get()
            .addOnSuccessListener {
                tvTitulo.setText("${it.get("titulo").toString()}")
                etDescripcion.setText("${it.get("descripcion").toString()}")
                etLikes.setText("${it.get("likes").toString()}")
            }
            .addOnFailureListener {  }

        val btnActualizar = findViewById<Button>(R.id.btn_actualizar_novela)
        btnActualizar.setOnClickListener {
            val novela = hashMapOf(
                "titulo" to tvTitulo.text.toString(),
                "descripcion" to etDescripcion.text.toString(),
                "likes" to etLikes.text.toString().toInt()
            )
            db.collection("novelas").document(idNovela)
                .set(novela)
                .addOnSuccessListener {
                    val intentNovela = Intent(this, ANovelas::class.java)
                    startActivity(intentNovela)
                }
                .addOnFailureListener {
                }

        }

    }
}