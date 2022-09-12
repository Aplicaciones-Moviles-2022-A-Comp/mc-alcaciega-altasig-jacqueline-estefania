package com.example.proyecto2b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AVerNovela : AppCompatActivity() {

    var idNovela = "";

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aver_novela)

        idNovela = intent.getStringExtra("id").toString()

        val tvTitulo = findViewById<TextView>(R.id.tv_ver_titulo)
        val tvDescripcion = findViewById<TextView>(R.id.tv_ver_descripcion)
        val tvLikes = findViewById<TextView>(R.id.tv_ver_likes)

        val db = Firebase.firestore
        val novelasRef = db
            .collection("novelas")
        novelasRef
            .document(idNovela)
            .get()
            .addOnSuccessListener {
                tvTitulo.setText("${it.get("titulo").toString()}")
                tvDescripcion.setText("${it.get("descripcion").toString()}")
                tvLikes.setText("${it.get("likes").toString()}")
            }
            .addOnFailureListener {  }

    }

}