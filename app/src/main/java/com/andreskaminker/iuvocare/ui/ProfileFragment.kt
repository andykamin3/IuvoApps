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
import com.andreskaminker.iuvocare.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {
    private lateinit var v: View
    private lateinit var buttonLogOut: Button
    private lateinit var buttonChangePwd: Button
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        buttonLogOut = v.findViewById(R.id.logOutButton)
        buttonChangePwd = v.findViewById(R.id.changePwdButton)
        return binding.root
    }



    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        binding.logOutButton.setOnClickListener {
            auth.signOut()
            Snackbar.make(v, "Logged out", Snackbar.LENGTH_SHORT)
            val intent = Intent(requireActivity(), StartActivity::class.java)
            startActivity(intent)
        }
        binding.changePwdButton.setOnClickListener{
            auth.sendPasswordResetEmail(
                auth.currentUser?.email.toString()
            ).addOnSuccessListener {
                Snackbar.make(binding.root, "Email enviado, revisá tu casilla de correo.", Snackbar.LENGTH_SHORT)
            }
        }

        super.onStart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
}
