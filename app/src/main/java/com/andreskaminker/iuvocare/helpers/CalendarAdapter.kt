package com.andreskaminker.iuvocare.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvocare.databinding.CalendarEventMedicineBinding
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvoshared.entities.PatientActions
import com.andreskaminker.iuvoshared.helpers.FormatUtils
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

class CalendarAdapter(var calendarEvents: MutableList<PatientActions>, val fragment: Fragment) :
    RecyclerView.Adapter<CalendarAdapter.CalendarElement>() {
    private val TAG = "CalendarAdapter"
    private lateinit var binding: CalendarEventMedicineBinding

    inner class CalendarElement(viewBinding: CalendarEventMedicineBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun renderAppointment(position: Int) {
            val element = calendarEvents[position] as Appointment
            Log.d(TAG, element.toString())
            binding.textViewHourScheduled.text = fragment.getString(R.string.time_placeholder,element.scheduledFor.toLocalTime())
            binding.textViewTitleEvent.text = element.title
            }

        fun renderMedication(position: Int) {
            val element = calendarEvents[position] as MedicationRequest
            binding.calendarDotView.setBackgroundResource(R.drawable.yellow_dot)
            binding.textViewTitleEvent.text = element.medication
            binding.textViewHourScheduled.text = fragment.getString(R.string.time_placeholder,element.takeTime.toLocalTime())
        }
    }


    fun setData(newData: MutableList<PatientActions>) {
        this.calendarEvents = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarElement {
        binding =
            CalendarEventMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarElement(viewBinding = binding)
    }

    override fun getItemCount(): Int {
        return calendarEvents.size
    }


    override fun onBindViewHolder(holder: CalendarElement, position: Int) {
        if (calendarEvents[position] is Appointment) {
            holder.renderAppointment(position)
        } else if (calendarEvents[position] is MedicationRequest) {
            holder.renderMedication(position)
        } else {
            throw IllegalStateException("There should not be other types of elements")
        }
        /*
        when (calendarEvents[position].kind) {
            ActionKind.APPOINTMENT -> {
                holder.renderAppointment(position)
            }
            ActionKind.MEDICATION -> {
                holder.renderMedication(position)
            }
            else -> {
                throw Exception("There should not be other types of elements other than Appointment and Medication")
            }
        }

         */
    }

}
