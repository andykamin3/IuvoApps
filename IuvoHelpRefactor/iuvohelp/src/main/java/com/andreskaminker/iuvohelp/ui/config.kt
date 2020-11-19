package com.andreskaminker.iuvohelp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.andreskaminker.iuvohelp.MainActivity
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvohelp.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvohelp.ui.dialogs.TimePickerFragment
import com.andreskaminker.iuvoshared.entities.Patient
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [config.newInstance] factory method to
 * create an instance of this fragment.
 */
class config : Fragment() {
    private val TAG = "config"
    private lateinit var v: View
    private lateinit var btnConfirmCodVinculacion: Button
    private lateinit var btnNumEmergencia: Button
    private lateinit var edNumEmergencyNumber : EditText
    private lateinit var edCodVinculacion: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_config, container, false)
        btnConfirmCodVinculacion = v.findViewById(R.id.btnConfirmarCodVinculacion)
        btnNumEmergencia = v.findViewById(R.id.btnNumEmergencia)
        edNumEmergencyNumber = v.findViewById(R.id.edNumEmergencia)
        edCodVinculacion = v.findViewById(R.id.edtCodVinculacion)
        updateUI()
        return v
    }

    private fun updateUI() {
        val mActivity = requireActivity() as MainActivity
        btnNumEmergencia.setOnClickListener {
            addEmergencyNumber()
        }
    }



    private fun addEmergencyNumber() {
        super.onStart()
        val emergencyNumber  = edNumEmergencyNumber.text.toString()
        val prefs_num = "numemergency"
        val prefs: SharedPreferences= requireContext().getSharedPreferences(prefs_num, 0)

        if (emergencyNumber != "") {
            val editor = prefs.edit()

            editor.putString(prefs_num, emergencyNumber)
            editor.apply()


            Navigation.findNavController(v).navigate(R.id.action_config2_to_homefrag)

        } else {
            Snackbar
                .make(v, "Por favor completar los campos obligatorios", Snackbar.LENGTH_SHORT)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .show()
        }
    }


}