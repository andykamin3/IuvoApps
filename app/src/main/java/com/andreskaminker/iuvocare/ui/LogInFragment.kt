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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


//
class LogInFragment : Fragment() {
    //TODO: Add login functionality and ToS, etc.
    private lateinit var v: View
    private lateinit var logInButton: Button
    private lateinit var auth: FirebaseAuth
    private val TAG = "LogInFragment"
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var googleButton: Button
    // Configure Google Sign In
    lateinit var gso : GoogleSignInOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    private fun signIn() {
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    goToHome()

                } else {
                    // If sign in fails, display a message to the user.
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


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account!!.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_log_in, container, false)
        editTextUsername = v.findViewById(R.id.editTextEmail)
        editTextPassword = v.findViewById(R.id.editTextPassword)
        logInButton = v.findViewById(R.id.logInButton)
        googleButton = v.findViewById(R.id.googleLogInButton)
        return v
    }

    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToHome()
        }


        super.onStart()
        googleButton.setOnClickListener {
            signIn()
        }


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

    companion object{
        const val RC_SIGN_IN = 777
    }
}