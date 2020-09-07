package com.andreskaminker.iuvocare.room.viewmodel

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.andreskaminker.iuvocare.room.repositories.HelperRepository
import com.andreskaminker.iuvoshared.entities.Helper
import com.andreskaminker.iuvoshared.entities.Patient

class HelperViewModel(application: Application) : AndroidViewModel(application){
    private val helperRepository: HelperRepository

    val helper: MutableLiveData<Helper> = MutableLiveData()
    init {
        helperRepository = HelperRepository()
        Log.d(TAG, "Initialized")
        retrieveHelper()
    }

    companion object{
        const val TAG = "HelperViewModel"
    }

    private fun retrieveHelper():MutableLiveData<Helper>{
        helperRepository.helperReference?.get()
            ?.addOnSuccessListener {
                Log.d(TAG, "Helper Retrieved")
                helper.value =  it.toObject(Helper::class.java)
            }
            ?.addOnFailureListener{
                Log.d(TAG, "Failure retrieving user")
                helper.value = null
            }
        return helper
    }

}