package com.revandika.revanchat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUsername: EditText = findViewById(R.id.editTextUsername)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textViewSignUp: TextView = findViewById(R.id.textViewSignUp)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isEmpty()) {
                showToast(getString(R.string.input_email))
            } else if (password.isEmpty()) {
                showToast(getString(R.string.input_password))
            } else {
                signIn(username, password)
            }
        }

        textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun signIn(username: String, password: String) {
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    showToast(getString(R.string.login_successful))
                    // Add code to navigate to the next screen or perform other actions
                } else {
                    // If sign in fails, display a message to the user.
                    showFailedSignInAlert()
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showFailedSignInAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.login_failed))
        builder.setMessage(getString(R.string.invalid_credentials))
        builder.setPositiveButton(android.R.string.ok) { _, _ -> }
        builder.show()
    }
}
