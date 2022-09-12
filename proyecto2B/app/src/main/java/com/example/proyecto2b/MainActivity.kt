package com.example.proyecto2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLibros = findViewById<Button>(R.id.btn_ir_novelas)
        btnLibros
            .setOnClickListener {
                openActivity( ANovelas::class.java )
            }

        val btnLogin = findViewById<Button>(R.id.btn_ir_login)
        btnLogin
            .setOnClickListener {
                login()
            }

        val btnLogout = findViewById<Button>(R.id.btn_ir_logout)
        btnLogout
            .setOnClickListener {
                logout()
            }

    }

    fun openActivity(
        clase: Class<*>,
    ) {
        val intent = Intent( this,clase )
        startActivity( intent )
    }

    fun login(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .build(),
            200
        )
    }

    fun logout(){
        AuthUI
            .getInstance()
            .signOut(this)
            .addOnCompleteListener {
                FAuthenticate.usuario = null
                setBienvenida()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            200 -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario: IdpResponse? = IdpResponse.fromResultIntent(data)
                    if (usuario != null) {
                        if (usuario.isNewUser == true) {
                            Log.i("firebase-login", "Nuevo Usuario")
                            registrarUsuario(usuario)
                        } else {
                            setUsuarioFirebase()
                            Log.i("firebase-login", "Antiguo Usuario")
                        }
                    }
                } else {
                    Log.i("firebase-login", "Login Cancelado")
                }
            }
        }
    }

    fun registrarUsuario(usuario: IdpResponse) {
        val usuarioLog = FirebaseAuth
            .getInstance()
            .getCurrentUser()
        if (usuario.email != null && usuarioLog != null) {
            val db = Firebase.firestore
            val roles = arrayListOf("usuario")
            val email = usuario.email
            val uid = usuarioLog.uid
            val nuevoUsuario = hashMapOf<String, Any>(
                "roles" to roles,
                "uid" to uid,
                "email" to email.toString()
            )
            db.collection("usuarios")
                .document(email.toString())
                .set(nuevoUsuario)
                .addOnSuccessListener {
                    Log.i("firebase-firestore", "Creación Exitosa")
                    setUsuarioFirebase()
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Creación Fallida")
                }
        }else{
            Log.i("firebase-login", "Error")
        }
    }

    fun setUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        if (usuarioLocal != null) {
            if (usuarioLocal.email != null) {
                val db = Firebase.firestore
                val referencia = db
                    .collection("usuarios")
                    .document(usuarioLocal.email.toString())
                referencia
                    .get()
                    .addOnSuccessListener {
                        val usuarioCargado: FUsuarioDTO? =
                            it.toObject(FUsuarioDTO::class.java)
                        if(usuarioCargado != null){
                            FAuthenticate.usuario = usuarioCargado
                        }
                        setBienvenida()
                        Log.i("firebase-firestore", "Usuario cargado")
                    }
                    .addOnFailureListener {
                        Log.i("firebase-firestore", "Fallo cargar usuario")
                    }
            }
        }
    }

    fun setBienvenida(){
        val botonLogin = findViewById<Button>(R.id.btn_ir_login)
        val botonLogout = findViewById<Button>(R.id.btn_ir_logout)
        if(FAuthenticate.usuario != null){
            botonLogin.visibility = View.INVISIBLE
            botonLogout.visibility = View.VISIBLE
        }else{
            botonLogin.visibility = View.VISIBLE
            botonLogout.visibility = View.INVISIBLE
        }
    }

}