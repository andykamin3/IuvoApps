package com.andreskaminker.iuvocare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andreskaminker.iuvocare.databinding.FragmentGenerateCodeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.roundToInt

class GenerateCodeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var _binding : FragmentGenerateCodeBinding? = null
    val binding get() = _binding!!
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGenerateCodeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        var codeNumber = (Math.random() * 1000000).roundToInt().toString().padStart(6, '0')
        CoroutineScope(context = Dispatchers.Main).launch {
            showAndCheckCode(codeNumber)
        }




    }

    private suspend fun GenerateCodeFragment.showAndCheckCode(codeNumber: String) {
        var codeNumber1 = codeNumber
        if (checkIfCodeIsAvailable(codeNumber1)) {
            binding.codeGeneratedTextView.text = codeNumber1
            auth.currentUser?.uid?.let {
                db.collection("linking").document(it).set(
                    CodeLink(it, codeNumber1, Timestamp.now())
                ).addOnFailureListener {
                    Snackbar.make(
                        requireView(),
                        "No podemos conectarnos. Intenta nuevamente",
                        Snackbar.LENGTH_SHORT
                    )
                }
            }
        } else {
            codeNumber1 = (Math.random() * 1000000).roundToInt().toString().padStart(6, '0')
            showAndCheckCode(codeNumber1)
        }
    }

    suspend fun checkIfCodeIsAvailable(code:String): Boolean {
        val result = db.collection("linking").whereEqualTo("codeNumber", code).get().await()
        return result.isEmpty
    }

    companion object {
        @JvmStatic
        fun newInstance() = GenerateCodeFragment()

    }


}

class CodeLink (val helperUid: String, val code: String, val timestamp: Timestamp)