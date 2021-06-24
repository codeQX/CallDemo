package com.example.test

import android.os.Build
import android.telecom.Call
import android.telecom.InCallService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class MyInCallService:InCallService() {

    private val callback:Call.Callback = object : Call.Callback(){
        override fun onStateChanged(call: Call?, state: Int) {
            super.onStateChanged(call, state)
            when(state){
                Call.STATE_ACTIVE->{

                }

                Call.STATE_DISCONNECTED->{

                }
            }
        }
    }

    override fun onCallAdded(call: Call?) {
        super.onCallAdded(call)

        call?.registerCallback(callback)
    }

    override fun onCallRemoved(call: Call?) {
        super.onCallRemoved(call)

        call?.unregisterCallback(callback)
    }
}