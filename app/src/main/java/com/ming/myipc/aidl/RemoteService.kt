package com.ming.myipc.aidl

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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
        return object : RemoteAidl.Stub() {
            override fun getRandom(): Int {
                Log.d("WTF", "getRandom")
                return Random.nextInt(100)
            }

            override fun getData(): RemotePar {
                Log.d("WTF", "getData")
                return RemotePar(Random.nextInt(100))
            }

            override fun getBundle(): Bundle {
                Log.d("WTF", "getBundle")
                val data: Bundle = Bundle()
                data.putParcelable("DATA", RemotePar(Random.nextInt(100)))
                return data
            }
        }
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