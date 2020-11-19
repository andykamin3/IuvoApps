package com.andreskaminker.iuvohelp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.andreskaminker.iuvohelp.R
import com.google.android.material.tabs.TabLayout

class HomeTabbedScreen : Fragment() {

    private lateinit var v: View
    private lateinit var homeScreenAdapter: ScreenPageAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)

        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = v.findViewById(R.id.home_pager)
        tabLayout = v.findViewById(R.id.home_layout)
        homeScreenAdapter = ScreenPageAdapter(childFragmentManager)
        viewPager.adapter = homeScreenAdapter
    }

    inner class ScreenPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                SeeMedicationFragment.newInstance()
            } else {
                SeeAppointmentFragment.newInstance()
            }
        }

        override fun getCount(): Int = 2
        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                "Medicaciones"
            } else {
                "Turnos Médicos"
            }
        }
    }
}