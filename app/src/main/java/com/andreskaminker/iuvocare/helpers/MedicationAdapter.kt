package com.andreskaminker.iuvocare.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvoshared.entities.Config
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvoshared.helpers.mapToWeekday
import com.andreskaminker.iuvocare.ui.SeeMedicationFragment
import com.andreskaminker.iuvoshared.helpers.mapToABP
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import org.threeten.bp.format.TextStyle

class MedicationAdapter(
    val parent: Fragment
) :
    RecyclerView.Adapter<MedicationAdapter.MedicationHolder>() {
    private var medicationList: List<MedicationRequest> = emptyList()

    class MedicationHolder(cv: View) : RecyclerView.ViewHolder(cv) {
        val textViewMedicationName: TextView = cv.findViewById(R.id.textViewMedicationName)
        val textViewWeekdays: TextView = cv.findViewById(R.id.textViewMedicationDate)
        val imageView: ImageView = cv.findViewById(R.id.imageViewMedication)
        val textViewTime: TextView = cv.findViewById(R.id.textViewMedicationTime)
        val cardView = cv.findViewById<CardView>(R.id.cardViewMedication)
        val deleteButton: Button = cv.findViewById(R.id.deleteMedicationButton)
        //TAB

    }

    fun setData(newData: List<MedicationRequest>) {
        medicationList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_medication, parent, false)
        return MedicationHolder(cardView)
    }

    override fun getItemCount(): Int {
        return medicationList.size
    }

    override fun onBindViewHolder(holder: MedicationHolder, position: Int) {
        holder.apply {

            textViewTime.text = parent.context?.getString(R.string.time_placeholder, medicationList[position].takeTime.toLocalTime())
            textViewMedicationName.text = medicationList[position].medication

            var weekString = ""
            medicationList[position].scheduledFor.map {
                weekString += (mapToWeekday(
                    it
                ).mapToABP()
                    .getDisplayName(TextStyle.FULL, Config.default_locale) + " ")
            }
            deleteButton.setOnClickListener {
                (parent as SeeMedicationFragment).deleteMedication(medicationList[position])
            }
            textViewWeekdays.text = parent.context?.getString(R.string.weekday_placeholder, weekString)
            val imgRef =
                FirebaseStorage.getInstance().reference.child(medicationList[position].imageURL)
            //TODO: add image view support with Glide
            Log.d(TAG, medicationList[position].imageURL)
            Glide.with(parent.requireActivity())
                .load(medicationList[position].imageURL)
                .centerCrop()
                .into(holder.imageView)

        }
    }


    companion object{
        val TAG = "MedicationAdapter"
    }

}