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

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var username: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth=FirebaseAuth.getInstance()
        username=findViewById(R.id.username_Register)
        email=findViewById(R.id.email_Register)
        password=findViewById(R.id.password_Register)

        val login=findViewById<TextView>(R.id.loginPage)
        login.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val register_btn=findViewById<Button>(R.id.register_btn)
        register_btn.setOnClickListener {

            if (username.text.isEmpty()){
                username.error="required this field"
            }
            if (email.text.isEmpty()){
                email.error="required this field"
            }
            if (password.text.isEmpty()){
                password.error="required this field"
            }else{
                register()
            }



        }

    }

    private fun register() {
        val email1=email.text.toString()
        val password1=password.text.toString()

        auth.createUserWithEmailAndPassword(email1,password1)
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        username.text.clear()
        email.text.clear()
        password.text.clear()

    }
}