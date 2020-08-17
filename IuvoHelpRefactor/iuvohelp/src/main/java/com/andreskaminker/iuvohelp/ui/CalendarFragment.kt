package com.andreskaminker.iuvohelp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreskaminker.iuvohelp.MainActivity
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvohelp.databinding.DayCalendarLayoutBinding
import com.andreskaminker.iuvohelp.databinding.FragmentCalendarBinding
import com.andreskaminker.iuvohelp.databinding.MonthLegendCalendarLayoutBinding
import com.andreskaminker.iuvohelp.helpers.CalendarAdapter

import com.andreskaminker.iuvohelp.room.viewmodel.AppointmentViewModel
import com.andreskaminker.iuvohelp.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvoshared.entities.*
import com.andreskaminker.iuvoshared.entities.Config.daysOfWeekFromLocale
import com.andreskaminker.iuvoshared.helpers.FormatUtils
import com.andreskaminker.iuvoshared.helpers.mapToABP
import com.andreskaminker.iuvoshared.helpers.mapToABPMonth
import com.andreskaminker.iuvoshared.helpers.mapToWeekday
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields

class CalendarFragment : Fragment() {
    private val TAG = "CalendarFragment"
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    companion object;


    private lateinit var v: View
    private lateinit var medicationRequests: MutableList<MedicationRequest>
    private lateinit var scheduledAppointments: MutableList<Appointment>
    private val currentPatient = Patient("123", "Andy", "andykamin3@gmail.com", "")
    private val today = LocalDate.now()
    private var selectedDate: LocalDate? = today

    private val appointmentViewModel: AppointmentViewModel by activityViewModels()
    private val medicationViewModel: MedicationViewModel by activityViewModels()

    class DayViewContainer(val viewRoot: View) : ViewContainer(viewRoot) {
        lateinit var day: CalendarDay
        val binding = DayCalendarLayoutBinding.bind(viewRoot)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        v = binding.root
        return v
    }

    override fun onStart() {
        super.onStart()
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Config.default_locale).firstDayOfWeek
        val daysOfWeek = daysOfWeekFromLocale()
        val listWeekDays = mutableListOf<DayOfWeek>()
        medicationViewModel.allMedications.observe(
            viewLifecycleOwner,
            Observer { medicationRequests ->
                for (medRequest in medicationRequests) {
                    for (day in medRequest.scheduledFor) {
                        listWeekDays.add(mapToWeekday(day).mapToABP())
                    }
                }
            })

        val listOfDates = mutableListOf<LocalDate>()
        appointmentViewModel.allAppointments.observe(viewLifecycleOwner, Observer { appointments ->
            appointments.map { appointment ->
                appointment.scheduledFor.retrieveLocalDate()?.let {
                    listOfDates.add(it)
                }

            }
        })

        binding.dateTextView.text = FormatUtils.selectionFormatter.format(today)
        binding.recyclerView.apply {
            adapter = CalendarAdapter(mutableListOf<PatientActions>())
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.calendarView.apply {
            setup(firstMonth, lastMonth, firstDayOfWeek)
            scrollToMonth(currentMonth)
        }

        class MonthViewContainer(viewRoot: View) : ViewContainer(viewRoot) {
            val binding = MonthLegendCalendarLayoutBinding.bind(viewRoot)
            val linearLayout = binding.legendLayout
        }

        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    if (container.linearLayout.tag == null) {
                        container.linearLayout.tag = month.yearMonth
                        container.linearLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].name.first().toString()
                                tv.setTextColor(
                                    resources.getColor(
                                        R.color.colorBlack,
                                        context?.theme
                                    )
                                )
                            }
                    }
                }

                override fun create(view: View): MonthViewContainer =
                    MonthViewContainer(viewRoot = view)
            }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer = DayViewContainer(view)


            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                container.binding.dayEditText.text = day.day.toString()

                if (container.day.owner == DayOwner.THIS_MONTH) {
                    when (container.day.date) {
                        selectedDate -> {
                            container.binding.dayEditText.background =
                                resources.getDrawable(R.drawable.example_3_today_bg, context?.theme)
                            container.binding.eventThatDay.visibility = View.GONE
                        }
                        else -> {
                            container.binding.dayEditText.background =
                                resources.getDrawable(R.drawable.bg_calendar_white, context?.theme)
                        }
                    }
                    when {
                        listWeekDays.contains(container.day.date.dayOfWeek) || listOfDates.contains(
                            container.day.date
                        ) -> {
                            container.binding.eventThatDay.visibility = View.VISIBLE
                            container.binding.eventThatDay.background =
                                resources.getDrawable(R.drawable.example_3_today_bg, context?.theme)
                            Log.d(TAG, "Found with ${container.day.date}")
                        }

                        else -> {
                            container.binding.eventThatDay.visibility = View.GONE
                        }
                    }

                    container.binding.root.setOnClickListener {
                        onDateSelected(container.day.date)
                    }
                } else {
                    container.binding.root.visibility = View.GONE
                }
            }
        }
        binding.calendarView.monthScrollListener = {
            binding.monthTitleTextView.text = if (it.year == today.year) {
                FormatUtils.titleSameYearFormatter.format(it.yearMonth)
            } else {
                FormatUtils.titleFormatter.format(it.yearMonth)
            }
            // Select the first day of the month when
            // we scroll to a new month.
            //selectDate(it.yearMonth.atDay(1))
        }
        updateUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateUI()
    }

    fun onDateSelected(newDate: LocalDate) {
        selectedDate = newDate
        binding.calendarView.notifyCalendarChanged()
        binding.dateTextView.text = FormatUtils.selectionFormatter.format(selectedDate)
        val newData = mutableListOf<PatientActions>()
        val mAdapter = (binding.recyclerView.adapter as CalendarAdapter)
        appointmentViewModel.allAppointments.observe(viewLifecycleOwner, Observer { appointments ->
            for (element in appointments) {
                val date = LocalDate.of(
                    element.scheduledFor.mYear!!,
                    mapToABPMonth(element.scheduledFor.mMonth!!),
                    element.scheduledFor.mDay!!
                )
                Log.d(TAG, FormatUtils.selectionFormatter.format(date))
                if (date.equals(selectedDate)) {
                    newData.add(element)
                }
            }
        })
        medicationViewModel.allMedications.observe(
            viewLifecycleOwner,
            Observer { medicationRequests ->
                for (element in medicationRequests) {
                    val weekDays = element.scheduledFor.map {
                        mapToWeekday(it).mapToABP()
                    }
                    if (weekDays.contains(selectedDate!!.dayOfWeek)) {
                        newData.add(element)
                    }
                }
            })

        mAdapter.setData(newData)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateUI() {
        val mActivity = requireActivity() as MainActivity
        mActivity.setFabDrawable(R.drawable.ic_baseline_home_24_b)
        mActivity.setFabColor(R.color.colorAccent)
        mActivity.setFabClickListener {
            val directions = CalendarFragmentDirections.actionCalendarFragmentToHomeTabbedScreen()
            v.findNavController().navigate(directions)
        }
    }
}


