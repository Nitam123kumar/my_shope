package com.example.myshope.AllFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myshope.AllDataModel.UserProfileDataModel
import com.example.myshope.LoginActivity
import com.example.myshope.ProductsDetails.MyOrdersActivity
import com.example.myshope.ProfileActivity
import com.example.myshope.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingFragment : Fragment() {

    lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    lateinit var profileImageEdit: ImageView
    var list = ArrayList<UserProfileDataModel>()
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

        db = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val profile = view.findViewById<ImageView>(R.id.profileImageView)
        val profileEdit = view.findViewById<LinearLayout>(R.id.editProfileLinearLayout)
        val profileName = view.findViewById<TextView>(R.id.profileNameTV)
        val profileEmail = view.findViewById<TextView>(R.id.profileEmailTV)
        profileImageEdit = view.findViewById(R.id.profileImageEdit)
        val progressBar=view.findViewById<ProgressBar>(R.id.progressBar)
        val myOrders = view.findViewById<LinearLayout>(R.id.myOrdersLinearLayout)

        progressBar.visibility= View.VISIBLE
//        val shareApp = view.findViewById<LinearLayout>(R.id.ShareAppLinearLayout)
        val logout = view.findViewById<LinearLayout>(R.id.logoutLinearLayout)
        logout.setOnClickListener {
            logoutAlertDialog()
        }

        myOrders.setOnClickListener {
            val intent = Intent(requireContext(), MyOrdersActivity::class.java)
            startActivity(intent)
        }

        profileEdit.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra("name", profileName.text.toString())
            intent.putExtra("email", profileEmail.text.toString())
            intent.putExtra("image", list[0].image.toString())
            startActivity(intent)
        }

        db.getReference("usersAccount").child(auth.currentUser!!.uid)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val data = snapshot.getValue(UserProfileDataModel::class.java)
                    list.add(data!!)
                    profileName.text = data.username
                    profileEmail.text = data.email
                    Glide.with(requireContext()).load(data.image).into(profileImageEdit)
                    Glide.with(requireContext()).load(data.image).into(profile)
                    progressBar.visibility=View.GONE

                }

                override fun onCancelled(error: DatabaseError) {
                    progressBar.visibility=View.GONE
                }
            })


        return view
    }

  private  fun logoutAlertDialog(){
        val dialog=AlertDialog.Builder(requireContext())
        dialog.setTitle("Logout")
        dialog.setMessage("Are you sure you want to logout?")
        dialog.setPositiveButton("Logout"){_,_->
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
        dialog.setNegativeButton("Cancel"){_,_->
            dialog.create().dismiss()
        }
        dialog.create().show()


    }

}