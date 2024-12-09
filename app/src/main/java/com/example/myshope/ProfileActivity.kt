package com.example.myshope

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {
    lateinit var fireStore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var fireStorage: FirebaseStorage
    var imageUri: Uri? = null
    lateinit var profileImageEdit: ImageView
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPhone: EditText
    lateinit var updateProfileButton: AppCompatButton
    lateinit var openGalleryCardView: androidx.cardview.widget.CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)


        fireStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        fireStorage = FirebaseStorage.getInstance()

        profileImageEdit = findViewById(R.id.profileImageEdit)
        editEmail = findViewById(R.id.profileEmailEdittext)
        editName = findViewById(R.id.profileNameEdittext)
        editPhone = findViewById(R.id.profilePhoneNumberEdittext)
        updateProfileButton = findViewById(R.id.updateProfileButton)
        openGalleryCardView = findViewById(R.id.openGalleryCardView)

        openGalleryCardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        updateProfileButton.setOnClickListener {
            profileImageUpload()
        }


    }

    fun insertProfileData(downloadUri: Uri) {
        val userId = auth.currentUser?.uid
        val name = editName.text.toString()
        val email = editEmail.text.toString()
        val phone = editPhone.text.toString()

        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "image" to downloadUri.toString()
        )

        fireStore.collection("users").document(userId!!).set(user)
            .addOnSuccessListener {
                finish()
            }


    }

    fun profileImageUpload() {
        val userId = auth.currentUser?.uid
        fireStorage.reference.child("profile_images").child(userId!!)
            .putFile(imageUri!!)
            .addOnSuccessListener { task ->
                task.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    insertProfileData(downloadUri)
                }

            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            profileImageEdit.setImageURI(imageUri)
        }

    }
}