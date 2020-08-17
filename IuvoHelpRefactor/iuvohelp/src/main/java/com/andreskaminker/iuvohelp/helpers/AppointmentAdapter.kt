package com.andreskaminker.iuvohelp.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvohelp.databinding.CardAppointmentBinding
import com.andreskaminker.iuvohelp.ui.SeeAppointmentFragment
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.helpers.FormatUtils
import com.andreskaminker.iuvoshared.helpers.mapToABPMonth
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class AppointmentAdapter(val fragment: Fragment) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {
    var appointmentList: List<Appointment> = emptyList<Appointment>()

    class AppointmentHolder(val binding: CardAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val binding =
            CardAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentHolder(binding)
    }

    override fun getItemCount(): Int = appointmentList.size

    fun setData(newData: List<Appointment>) {
        appointmentList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.binding.appointmentDescriptionCard.text = appointmentList[position].description
        holder.binding.appointmentTitleCard.text = appointmentList[position].title
        holder.binding.appointmentDateCard.text = formatStringDate(position)
        holder.binding.appointmentTimeCard.text = formatTime(position)
        holder.binding.buttonDelete.setOnClickListener {
            fragment as SeeAppointmentFragment
            fragment.deleteAppointment(appointmentList[position])
        }
    }

    private fun formatStringDate(position: Int): String {
        appointmentList[position].scheduledFor.run {
            val localDate = LocalDate.of(mYear!!, mapToABPMonth(mMonth!!), mDay!!)
            return localDate.format(FormatUtils.selectionFormatter)
        }
    }

    private fun formatTime(position: Int): String {
        appointmentList[position].scheduledFor.run {
            val localTime = LocalTime.of(mHour!!, mMinutes!!)
            return localTime.format(FormatUtils.timeFormatter)
        }
    }
}