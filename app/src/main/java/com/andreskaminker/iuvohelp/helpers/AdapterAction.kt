package com.andreskaminker.iuvohelp.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.entities.PatientActions

class AdapterAction(private val patientActions: ArrayList<PatientActions>) :
    RecyclerView.Adapter<AdapterAction.ActionViewHolder>() {
    class ActionViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val cv = cardView
        val textViewMain: TextView = cv.findViewById(R.id.textViewMain)
        val textViewKind: TextView = cv.findViewById(R.id.textViewKind)
        val textViewStatus: TextView = cv.findViewById(R.id.textViewStatus)
        val textViewTime: TextView = cv.findViewById(R.id.textViewMedicationTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_action, parent, false) as CardView
        return ActionViewHolder(cardView)
    }

    override fun getItemCount(): Int {
        return patientActions.size
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val element = patientActions[position] as Appointment
            holder.textViewMain.text = element.description
            holder.textViewTime.text =
                "${element.scheduledFor.mDay}-${element.scheduledFor.mMonth}-${element.scheduledFor.mYear}  ${element.scheduledFor.mHour}:${element.scheduledFor.mMinutes} "

            holder.textViewStatus.text = element.status.toString()


    }
}