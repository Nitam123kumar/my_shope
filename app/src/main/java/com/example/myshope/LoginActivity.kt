package com.example.myshope

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var email:EditText
    lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()
        email=findViewById(R.id.email_login)
        password=findViewById(R.id.password_login)
        val SignUpPage=findViewById<TextView>(R.id.SignUpPage)
        SignUpPage.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        val login_btn=findViewById<AppCompatButton>(R.id.login_btn)
        login_btn.setOnClickListener {
            login()
        }

    }
    private fun login(){

        val email1=email.text.toString()
        val password1=password.text.toString()
        if (email1.isEmpty()){
            email.error="required this field"
        }

        if (password1.length < 8) {
            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
        }

        else{
            auth.signInWithEmailAndPassword(email1,password1)
                .addOnSuccessListener {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    email.text.clear()
                    password.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Please Enter Your Right Email And Password", Toast.LENGTH_SHORT).show()
                }


        }
        }


}