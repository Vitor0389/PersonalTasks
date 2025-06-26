package com.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.personaltasks.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var senhaEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var googleSignInBtn: Button

    private val googleLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val credential = Identity.getSignInClient(this).getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnSuccessListener { startMain() }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro no login Google", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.emailEt)
        senhaEt = findViewById(R.id.senhaEt)
        loginBtn = findViewById(R.id.loginBtn)
        registerBtn = findViewById(R.id.registerBtn)
        googleSignInBtn = findViewById(R.id.googleSignInBtn)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val senha = senhaEt.text.toString().trim()
            if (email.isNotEmpty() && senha.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnSuccessListener { startMain() }
                    .addOnFailureListener {
                        Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        registerBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val senha = senhaEt.text.toString().trim()
            if (email.isNotEmpty() && senha.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Usuário criado!", Toast.LENGTH_SHORT).show()
                        startMain()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        googleSignInBtn.setOnClickListener {
            val request = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id)) // deve estar no strings.xml
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .build()


            Identity.getSignInClient(this).beginSignIn(request)
                .addOnSuccessListener {
                    googleLauncher.launch(
                        IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                    )
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Falha ao iniciar login Google", Toast.LENGTH_SHORT).show()
                    Log.e("LOGIN", "Google Sign-In failed", it)
                }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) startMain()
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
