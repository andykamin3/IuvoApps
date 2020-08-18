package com.andreskaminker.iuvocare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.andreskaminker.iuvocare.R
import com.google.android.material.tabs.TabLayout

class AuthFragment : Fragment() {

    private lateinit var v:View
    private lateinit var authScreenAdapter: ScreenPageAdapter
    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_auth, container, false)


        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = v.findViewById(R.id.pager)
        tabLayout = v.findViewById(R.id.tab_layout)
        authScreenAdapter = ScreenPageAdapter(childFragmentManager)
        viewPager.adapter = authScreenAdapter
    }

}

class ScreenPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    override fun getItem(position: Int): Fragment {
        return if(position == 0){
            SignUpFragment()
        } else{
            LogInFragment()
        }
    }

    override fun getCount(): Int  = 2

    override fun getPageTitle(position: Int) : CharSequence?{
        return if(position == 0){
            "Registrate"
        } else{
            "Inicia Sesión"
        }
    }

}