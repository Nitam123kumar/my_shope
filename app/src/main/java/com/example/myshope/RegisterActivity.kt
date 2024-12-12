package com.example.myshope

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var username: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var db: FirebaseDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        username = findViewById(R.id.username_Register)
        email = findViewById(R.id.email_Register)
        password = findViewById(R.id.password_Register)

        val login = findViewById<TextView>(R.id.loginPage)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val register_btn = findViewById<Button>(R.id.register_btn)
        register_btn.setOnClickListener {
            if (username.text.isEmpty()) {
                username.error = "required this username"
            }
          else  if (email.text.isEmpty()) {
                email.error = "required this email"
            }
            else if (!isGmail(email.text.toString())) {
                email.error = "Only Gmail accounts are allowed"
            }
            else if (password.text.isEmpty()) {
                password.error = "required this password"
            }

          else  if (password.length() < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            }
            else {
                register()
            }


        }

    }

    private fun register() {
        val email1 = email.text.toString()
        val password1 = password.text.toString()

        auth.createUserWithEmailAndPassword(email1, password1)
            .addOnSuccessListener {
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                val data = hashMapOf(
                    "image" to "",
                    "username" to username.text.toString(),
                    "email" to email.text.toString()
                )
                val user = auth.currentUser?.uid
                db.getReference("usersAccount").child(user.toString()).setValue(data)


                finish()
                username.text.clear()
                email.text.clear()
                password.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
            }


    }
    private fun isGmail(email: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9._%+-]+@gmail\\.com$")
        return regex.matches(email)
    }
}