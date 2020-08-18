package com.andreskaminker.iuvocare.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvocare.StartActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var v: View
    private lateinit var buttonLogOut: Button
    private lateinit var buttonChangePwd: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_profile, container, false)
        buttonLogOut = v.findViewById(R.id.logOutButton)
        buttonChangePwd = v.findViewById(R.id.changePwdButton)
        return v
    }

    private fun updateUI() {

    }

    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        buttonLogOut.setOnClickListener {
            auth.signOut()
            Snackbar.make(v, "Logged out", Snackbar.LENGTH_SHORT)
            val intent = Intent(requireActivity(), StartActivity::class.java)
            startActivity(intent)
        }

        super.onStart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateUI()
    }
}
