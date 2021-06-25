package com.example.test

import android.os.Build
import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccountHandle
import androidx.annotation.RequiresApi
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.M)
class MyConnectionService : ConnectionService(){

    //来电
    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Timber.d("onCreateIncomingConnection")
        return super.onCreateIncomingConnection(connectionManagerPhoneAccount, request)
    }

    override fun onCreateIncomingConnectionFailed(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ) {
        Timber.d("onCreateIncomingConnectionFailed")
        super.onCreateIncomingConnectionFailed(connectionManagerPhoneAccount, request)
    }

    override fun onCreateIncomingHandoverConnection(
        fromPhoneAccountHandle: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Timber.d("onCreateIncomingHandoverConnection")
        return super.onCreateIncomingHandoverConnection(fromPhoneAccountHandle, request)
    }

    //去电
    override fun onCreateOutgoingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Timber.d("onCreateOutgoingConnection")
        return super.onCreateOutgoingConnection(connectionManagerPhoneAccount, request)
    }

    override fun onCreateOutgoingConnectionFailed(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ) {
        Timber.d("onCreateOutgoingConnectionFailed")
        super.onCreateOutgoingConnectionFailed(connectionManagerPhoneAccount, request)
    }

    override fun onCreateOutgoingHandoverConnection(
        fromPhoneAccountHandle: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Timber.d("onCreateOutgoingHandoverConnection")
        return super.onCreateOutgoingHandoverConnection(fromPhoneAccountHandle, request)
    }



}