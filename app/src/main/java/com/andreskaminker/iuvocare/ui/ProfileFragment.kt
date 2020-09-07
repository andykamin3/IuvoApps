package com.andreskaminker.iuvocare.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

        binding.searchButtonProfile.setOnClickListener{
            val emailInfo = binding.emailSearchProfileEditText.text.toString().trim()
            userReference.whereEqualTo("email", emailInfo)
                .get()
                .addOnSuccessListener {
                    Log.d(TAG, "Success")
                    if(it.isEmpty) {
                        Log.d(TAG, "Search not succesful")
                        TODO("ALERT USER")
                    } else{
                        val ml = it.toObjects(Patient::class.java)
                        Log.d(TAG, ml.toString())
                        val patient = ml[0]
                        binding.include.nameAndSurnameProfile.text = patient.email
                        binding.include.addPersonText.setOnClickListener{
                            if(userViewModel.helper.value != null){
                                val request = Request(patient, userViewModel.helper.value!!)
                                request
                            }

                        }
                    }
                }
                .addOnFailureListener{
                    Snackbar.make(binding.root,"No encontramos a la persona que buscabas. Volvé a intentarlo", Snackbar.LENGTH_SHORT)
                }
        }
        super.onStart()
    }

    companion object{
        private const val TAG = "ProfileFragment"
    }



}

class Request(
    val patient: Patient,
    val user: Helper,
    val timestamp: Timestamp = Timestamp.now()
)
