package com.ming.myipc.aidl

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.ming.myipc.messengerservice.Test
import kotlin.random.Random

/**
 * Created by zh on 2022/9/19.
 */
class RemoteService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("WTF", "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("WTF", "onBind")
        return Test()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("WTF", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("WTF", "onDestroy")
        super.onDestroy()
    }
}