package com.ming.myipc.startservice

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log

/**
 * Created by zh on 2022/9/16.
 */
@Deprecated("IntentService 在Android11-API30中已废弃")
class StartIntentService : IntentService("StartIntentService") {

    companion object {
        const val TASK_ADD: String = "task_add"
        const val TASK_REMOVE: String = "task_remove"

        fun start(context: Context, taskType: String, data: String) {
            val intent: Intent = Intent()
            intent.setClass(context, StartIntentService::class.java)
            intent.action = taskType
            intent.putExtra("DATA", data)
            context.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("WTF", "onCreate UI Thread?= ${Looper.myLooper() == Looper.getMainLooper()}")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d("WTF", "onStart UI Thread?= ${Looper.myLooper() == Looper.getMainLooper()}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("WTF", "onStartCommand UI Thread?= ${Looper.myLooper() == Looper.getMainLooper()}")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WTF", "onDestroy UI Thread?= ${Looper.myLooper() == Looper.getMainLooper()}")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("WTF", "onHandleIntent UI Thread?= ${Looper.myLooper() == Looper.getMainLooper()}")
        when (intent?.action) {
            TASK_ADD -> {
                Log.d("WTF", "Add...")
                Thread.sleep(3000)
                Log.d("WTF", "Add:${intent.getStringExtra("DATA")} success")
            }
            TASK_REMOVE -> {
                Log.d("WTF", "Remove...")
                Thread.sleep(3000)
                Log.d("WTF", "Remove:${intent.getStringExtra("DATA")} success")
            }
            else -> {
                //NO OP
            }
        }
    }
}