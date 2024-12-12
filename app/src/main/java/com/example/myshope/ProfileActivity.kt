package com.example.myshope

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {
    lateinit var db: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var fireStorage: FirebaseStorage
    var imageUri: Uri? = null
    lateinit var profileImageEdit: ImageView
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var updateProfileButton: AppCompatButton
    lateinit var openGalleryCardView: androidx.cardview.widget.CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)


        db = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        fireStorage = FirebaseStorage.getInstance()

        profileImageEdit = findViewById(R.id.profileImageEdit)
        editEmail = findViewById(R.id.profileEmailEdittext)
        editName = findViewById(R.id.profileNameEdittext)
        updateProfileButton = findViewById(R.id.updateProfileButton)
        openGalleryCardView = findViewById(R.id.openGalleryCardView)
        val onBack=findViewById<ImageView>(R.id.onBack)
        onBack.setOnClickListener {
            onBackPressed()
        }

        openGalleryCardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val image = intent.getStringExtra("image")
        editName.setText(name)
        editEmail.setText(email)

        Glide.with(this).load(image).into(profileImageEdit)

        updateProfileButton.setOnClickListener {
            if (editName.text.isEmpty()) {
                editName.error = "Please enter your name"
            }
            if (editEmail.text.isEmpty()) {
                editEmail.error = "Please enter your email"
            }
            if (imageUri == null) {
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show()
            } else {
                profileImageUpdate()
            }
        }


    }

    fun updateProfileData(downloadUri: Uri) {
        val progressDialog = android.app.ProgressDialog(this)
        progressDialog.setMessage("Updating Profile...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val userId = auth.currentUser?.uid
        val name = editName.text.toString()
        val email = editEmail.text.toString()

        val user = hashMapOf(
            "username" to name,
            "email" to email,
            "image" to downloadUri.toString()
        )

        db.getReference("usersAccount").child(userId!!).updateChildren(user as Map<String, Any>)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profile Update Success", Toast.LENGTH_SHORT).show().apply {
                    finish()
                }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profile Update Failed", Toast.LENGTH_SHORT).show()
            }


    }

    fun profileImageUpdate() {
        val userId = auth.currentUser?.uid
        fireStorage.reference.child("profile_images").child(userId!!)
            .putFile(imageUri!!)
            .addOnSuccessListener { task ->
                task.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    updateProfileData(downloadUri)
                }

            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            if (imageUri != null) {
                // Glide se image set karo
                Glide.with(this)
                    .load(imageUri)
                    .into(profileImageEdit)
            } else {
                Toast.makeText(this, "Failed to get image from gallery", Toast.LENGTH_SHORT).show()
            }
        }

    }
}