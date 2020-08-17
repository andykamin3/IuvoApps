package com.andreskaminker.iuvohelp.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvohelp.databinding.CalendarEventMedicineBinding
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvoshared.entities.PatientActions

class CalendarAdapter(var calendarEvents: MutableList<PatientActions>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarElement>() {
    private val TAG = "CalendarAdapter"
    private lateinit var binding: CalendarEventMedicineBinding

    inner class CalendarElement(viewBinding: CalendarEventMedicineBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun renderAppointment(position: Int) {
            val element = calendarEvents[position] as Appointment
            Log.d(TAG, element.toString())
            binding.textViewTitleEvent.text = element.title
            if (element.scheduledFor.mMinutes != 0) {
                binding.textViewHourScheduled.text =
                    "${element.scheduledFor.mHour}:${element.scheduledFor.mMinutes}"
            } else {
                binding.textViewHourScheduled.text = "${element.scheduledFor.mHour}:00"
            }
        }

        fun renderMedication(position: Int) {
            val element = calendarEvents[position] as MedicationRequest
            binding.calendarDotView.setBackgroundResource(R.drawable.yellow_dot)
            binding.textViewTitleEvent.text = element.medication
            if (element.takeTime.minutes != 0) {
                binding.textViewHourScheduled.text =
                    "${element.takeTime.hour}:${element.takeTime.minutes}"
            } else {
                binding.textViewHourScheduled.text = "${element.takeTime.minutes}:00"
            }
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
