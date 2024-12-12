package com.example.myshope

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    lateinit var db: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var fireStorage: FirebaseStorage
    var imageUri: Uri? = null
    lateinit var profileImageEdit: ImageView
    lateinit var editName: EditText
    lateinit var editEmail: TextView
    lateinit var updateProfileButton: AppCompatButton
    lateinit var openGalleryCardView: androidx.cardview.widget.CardView
    lateinit var progressDialog: ProgressDialog

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
        val onBack = findViewById<ImageView>(R.id.onBack)
        onBack.setOnClickListener {
            onBackPressed()
        }
        editEmail.setOnClickListener {
            Toast.makeText(this, "Only View Email No Change", Toast.LENGTH_SHORT).show()
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
            progressDialog = ProgressDialog(this)
            updateProfile()
        }


    }

    private fun updateProfile() {
        if (editName.text.isEmpty()) {
            editName.error = "Please enter your name"
            return
        }

        if (editEmail.text.isEmpty()) {
            Toast.makeText(this, "Only View Email No Changes", Toast.LENGTH_SHORT).show()
        }

        progressDialog.setMessage("Updating Profile...")
        progressDialog.show()

        lifecycleScope.launch {
            uploadImageAndUpdateProfile()
        }
    }

    fun updateProfileData(downloadUri: String) {


        val userId = auth.currentUser?.uid
        val name = editName.text.toString()
        val email = editEmail.text.toString()

        val user = hashMapOf<String, Any>()
        user["username"] = name
        user["email"] = email
        if (downloadUri != "") {
            user["image"] = downloadUri
        }


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

    private fun uploadImageAndUpdateProfile() {
        if (imageUri != null) {
            val userId = auth.currentUser?.uid ?: return
            fireStorage.reference.child("profile_images/${userId}.jpg\"")
                .putFile(imageUri!!)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                        updateProfileData(downloadUri.toString())
                    }
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            updateProfileData("")
            progressDialog.dismiss()
            finish()
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