package com.andreskaminker.iuvocare.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvoshared.entities.Patient

class UserConfigurationAdapter(private val patientActions: ArrayList<Patient>) :
    RecyclerView.Adapter<UserConfigurationAdapter.UserConfigurationHolder>() {
    class UserConfigurationHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val cv = cardView
        //val textViewMain: TextView = cv.findViewById(R.id.)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserConfigurationHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_user, parent, false) as CardView
        return UserConfigurationHolder(cardView)
    }

    override fun getItemCount(): Int {
        return patientActions.size
    }

    override fun onBindViewHolder(holder: UserConfigurationHolder, position: Int) {


    }
}