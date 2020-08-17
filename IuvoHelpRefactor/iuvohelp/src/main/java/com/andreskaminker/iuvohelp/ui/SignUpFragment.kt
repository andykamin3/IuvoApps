package com.andreskaminker.iuvohelp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvoshared.entities.Helper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {
    private lateinit var v: View
    private val TAG = "SignUpFragment"
    private lateinit var buttonSignUp: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextName: EditText
    val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sign_up, container, false)
        buttonSignUp = v.findViewById(R.id.logInButton)
        editTextEmail = v.findViewById(R.id.editTextEmail)
        editTextName = v.findViewById(R.id.editTextName)
        editTextPassword = v.findViewById(R.id.editTextPassword)
        return v
    }


    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        super.onStart()
        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                if (password.length > 8) {
                    createUser(email, password, name)
                } else {
                    Snackbar.make(
                        v,
                        resources.getString(R.string.pass_parameters),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                Snackbar.make(v, resources.getString(R.string.auth_error), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun createUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user!!.updateProfile(profileUpdates)
                    val helperData = Helper(
                        id = user.uid,
                        name_given = name,
                        email = email,
                        helped = ""
                    )
                    db.collection("helpers").document(helperData.id).set(helperData)
                    goToUserPreferences()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        v,
                        resources.getString(R.string.auth_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "Error: ${task.exception}")
                }

                // ...
            }
    }

    private fun goToUserPreferences() {
        //TODO: Go to user preferences
    }

}