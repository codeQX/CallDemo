package com.example.test

import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.InCallService
import androidx.annotation.RequiresApi
import timber.log.Timber

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
        Timber.d("onCallAdded")

        startService(Intent(this, MyRecordService::class.java))

        call?.registerCallback(callback)
    }

    override fun onCallRemoved(call: Call?) {
        super.onCallRemoved(call)
        Timber.d("onCallRemoved")

        call?.unregisterCallback(callback)
    }
}