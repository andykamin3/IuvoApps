package com.andreskaminker.iuvocare.ui

import android.Manifest
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import ca.antonious.materialdaypicker.MaterialDayPicker
import com.andreskaminker.iuvocare.MainActivity
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvocare.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvocare.room.viewmodel.PatientViewModel
import com.andreskaminker.iuvocare.ui.dialogs.TimePickerFragment
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvoshared.entities.Patient
import com.andreskaminker.iuvoshared.entities.TimeResult
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import java.io.ByteArrayOutputStream
import java.lang.IllegalStateException


class AddMedicationFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private val TAG = "AddMedicationFragment"
    private lateinit var v: View
    private lateinit var timeButton: Button
    private lateinit var materialPicker: MaterialDayPicker
    private lateinit var timeResult: TimeResult
    private var timeSet = false
    private var imageSet = false
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageButton: Button
    private lateinit var fabMedication : FloatingActionButton
    private val medicationViewModel: MedicationViewModel by activityViewModels()
    private val patientViewModel: PatientViewModel by activityViewModels()
    private lateinit var storage: FirebaseStorage
    private lateinit var imgRef: String
    private var imageLocalUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_medication, container, false)
        materialPicker = v.findViewById(R.id.day_picker)
        timeButton = v.findViewById(R.id.timeButton)
        nameEditText = v.findViewById(R.id.editTextMedicationName)
        descriptionEditText = v.findViewById(R.id.editTextMedicationDescription)
        imageButton = v.findViewById(R.id.imageButton)
        fabMedication = v.findViewById(R.id.fabAddMedication)
        return v
    }

    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            uploadImage()
        }
        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Snackbar.make(v, "Necesitamos que nos des permisos.", Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun uploadImage() {

        TedBottomPicker.with(requireActivity()).show {
            imageLocalUri = it
        }
    }

    private fun updateUI() {
        val mActivity = requireActivity() as MainActivity
        patientViewModel.patient.observe(viewLifecycleOwner, Observer {patient->
            fabMedication.setOnClickListener{
                val medicationName = nameEditText.text.toString()
                val medicationDescription = descriptionEditText.text.toString()
                val weekDays = arrayListOf<Int>()
                materialPicker.selectedDays.map {
                    weekDays.add(it.ordinal)
                }
                if (timeSet && medicationName != "" && weekDays.isNotEmpty() && imageLocalUri != null) {
                val fileRef =
                    Firebase.storage.reference.child(patientViewModel.patient.value?.patId + Timestamp.now().seconds.toString())
                val uploadTask = fileRef.putFile(
                    imageLocalUri!!
                )
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    fileRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        imgRef = downloadUri.toString()
                        Log.d(TAG, downloadUri.toString())
                        val medicationRequest =
                            MedicationRequest(
                                id = "1233",
                                patient = patient,
                                medicationName = medicationName,
                                scheduledFor = weekDays,
                                imageUrl = imgRef, //TODO: Change image when uploaded
                                takeTime = timeResult
                            )
                        medicationViewModel.addMedication(medicationRequest)

                        val directions =
                            AddMedicationFragmentDirections.actionAddMedicationFragmentToHomeTabbedScreen()
                        v.findNavController().navigate(directions)
                        //imageReference = currentUser.uid+Timestamp.now().seconds.toString()
                    } else {
                        Snackbar.make(
                            v,
                            "Volvé a intentarlo.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }


            }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        storage = Firebase.storage
        timeButton.setOnClickListener {
            TimePickerFragment()
                .show(childFragmentManager, "timePicker")
        }
        imageButton.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission_group.STORAGE)
                .check()
        }
        updateUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    private fun addMedication(patient: Patient) {




    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        timeResult = TimeResult(
            hour = hourOfDay,
            minutes = minute
        )
        timeSet = true
    }

    companion object{
        const val REQUEST_IMAGE_CAPTURE = 1122
    }


    //https://github.com/gantonious/MaterialDayPicker for the implementation of the week day picker using Calendar

}