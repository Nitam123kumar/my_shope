package com.example.myshope.AllFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myshope.ProfileActivity
import com.example.myshope.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingFragment : Fragment() {

    lateinit var fireStore: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        fireStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val profile = view.findViewById<ImageView>(R.id.profileImageView)
        val profileEdit = view.findViewById<ImageView>(R.id.editProfileImageView)
        val profileName = view.findViewById<TextView>(R.id.profileNameTV)
        val profileEmail = view.findViewById<TextView>(R.id.profileEmailTV)
        val profilePhone = view.findViewById<TextView>(R.id.profilePhoneNumberTV)


        profileEdit.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        fireStore.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()) {
                val image = it.getString("image")
                val name = it.getString("name")
                val email = it.getString("email")
                val phone = it.getString("phone")

                profileName.text = name
                profileEmail.text = email
                profilePhone.text = phone
                if (image != "") {
                    Glide.with(requireContext()).load(image).into(profile)
                }
            }
        }


        return view
    }

}