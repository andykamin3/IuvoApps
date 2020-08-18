package com.andreskaminker.iuvocare.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.andreskaminker.iuvocare.MainActivity
import com.andreskaminker.iuvocare.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

//
class LogInFragment : Fragment() {
    //TODO: Add login functionality and ToS, etc.
    private lateinit var v: View
    private lateinit var logInButton: Button
    private lateinit var auth: FirebaseAuth
    private val TAG = "LogInFragment"
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_log_in, container, false)
        editTextUsername = v.findViewById(R.id.editTextEmail)
        editTextPassword = v.findViewById(R.id.editTextPassword)
        logInButton = v.findViewById(R.id.logInButton)
        return v
    }

    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToHome()
        }

        super.onStart()
        logInButton.setOnClickListener {
            val password = editTextPassword.text.toString().trim()
            val email = editTextUsername.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        goToHome()
                    } else {
                        //TODO: Build more helpful error messages for the user
                        Snackbar.make(
                            v,
                            resources.getString(R.string.auth_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "Error: ${task.exception}")
                    }
                }
            } else {
                Snackbar.make(v, resources.getString(R.string.auth_error), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goToHome() {
        val mIntent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(mIntent)
    }
}