package com.andreskaminker.iuvohelp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.andreskaminker.iuvohelp.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.toolbar.*


class navbar : Fragment() {
    private lateinit var v: View
    private lateinit var iConfig: ImageView
    private  var navController: NavController? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.toolbar, container, false)
        iConfig = v.findViewById(R.id.ivAjustes)
        return v
    }

    override fun onStart() {
        super.onStart()
        iConfig.setOnClickListener {
            Snackbar.make(v, resources.getString(R.string.auth_error), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

}