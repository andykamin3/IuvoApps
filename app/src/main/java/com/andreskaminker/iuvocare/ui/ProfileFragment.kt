package com.andreskaminker.iuvocare.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvocare.StartActivity
import com.andreskaminker.iuvocare.databinding.FragmentProfileBinding
import com.andreskaminker.iuvocare.room.viewmodel.HelperViewModel
import com.andreskaminker.iuvocare.room.viewmodel.PatientViewModel
import com.andreskaminker.iuvoshared.entities.Helper
import com.andreskaminker.iuvoshared.entities.Patient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.time.Instant
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var v: View
    private lateinit var buttonLogOut: Button
    private lateinit var buttonChangePwd: Button
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userReference = Firebase.firestore.collection("patients")
    private val requestReference = Firebase.firestore.collection("connection-requests")
    private val userViewModel: HelperViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        v = binding.root
        return v
    }



    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        super.onStart()
        binding.logOutButton.setOnClickListener {
            auth.signOut()
            val mIntent = Intent(requireContext(), StartActivity::class.java)
            startActivity(mIntent)
        }

        binding.buttonGenerateCode.setOnClickListener {
            val dir = ProfileFragmentDirections.actionProfileFragmentToGenerateCodeFragment()
            v.findNavController().navigate(dir)
        }

        binding.changePwdButton.setOnClickListener {
            //TODO: Implement.
        }
    }

    companion object{
        private const val TAG = "ProfileFragment"
    }



}

data class Request(
    val patient: Patient,
    val user: Helper,
    val timestamp: Timestamp = Timestamp.now(),
    val isActive: Boolean
)