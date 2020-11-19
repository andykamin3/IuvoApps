package com.andreskaminker.iuvohelp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvohelp.helpers.MedicationAdapter
import com.andreskaminker.iuvohelp.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.google.android.material.snackbar.Snackbar

class homefrag : Fragment() {

    private lateinit var v: View
    private lateinit var cardSeeMeds: CardView
    private lateinit var cardSeeAppoint: CardView
    private lateinit var medicationAdapter: MedicationAdapter
    private lateinit var medicationList: MutableList<MedicationRequest>
    private lateinit var recyclerView: RecyclerView
    private lateinit var EmergencyCallButton: Button
    private lateinit var stNum: String
    private  var navController: NavController? = null
    private val medicationViewModel: MedicationViewModel by activityViewModels()
    private lateinit var topAppBar: androidx.appcompat.widget.Toolbar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        medicationList = mutableListOf()
        medicationAdapter = MedicationAdapter(this)
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = medicationAdapter
            layoutManager = LinearLayoutManager(context)
        }
        medicationViewModel.allMedications.observe(
            viewLifecycleOwner,
            Observer { medicationsList ->
                medicationsList?.let {
                    medicationAdapter.setData(it as MutableList<MedicationRequest>)
                }
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        EmergencyCallButton = v.findViewById(R.id.btnEmergency)
        cardSeeAppoint = v.findViewById(R.id.cardAppointView)
        cardSeeMeds = v.findViewById(R.id.cardMedsView)
        recyclerView = v.findViewById(R.id.medsrecylcer)
        topAppBar = v.findViewById(R.id.topAppBar)
        val prefs_num = "numemergency"
        val prefs: SharedPreferences = requireContext().getSharedPreferences(prefs_num, 0)

        stNum = prefs.getString(prefs_num, "911").toString()

        topAppBar.setOnMenuItemClickListener() { menuItem ->
            when (menuItem.itemId) {
                R.id.config -> {
                    Navigation.findNavController(v).navigate(R.id.action_homefrag_to_config2)
                    true
                }
                else -> false
            }
        }



        return v
    }

    override fun onStart() {
        super.onStart()
        cardSeeAppoint.setOnClickListener {
            Navigation.findNavController(v).navigate(R.id.action_homefrag_to_addAppointmentFragment)

        }
        cardSeeMeds.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_homefrag_to_seeMedicationFragment)
        }
        EmergencyCallButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$stNum")

            startActivity(callIntent)
        }

    }

    //public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    //    if (requestCode == 1000) {
    //        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
    //            Toast.makeText(getContainerActivity(), "Comencemos!", Toast.LENGTH_SHORT).show();
    //
    //        }
    //        else{
    //            Toast.makeText(getContainerActivity(), "sos un muerto kpo!", Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //    else{
    //        Toast.makeText(getContainerActivity(), "que hiciste mal kpo!", Toast.LENGTH_SHORT).show();
    //    }
    //}

}

