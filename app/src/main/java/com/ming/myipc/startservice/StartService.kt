package com.ming.myipc.startservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log

/**
 * Created by zh on 2022/9/16.
 */
class StartService : Service() {

    companion object {

        fun start(context: Context, size: Int) {
            Intent().also {
                it.setClass(context, StartService::class.java)
                it.putExtra("DATA", size)
                context.startService(it)
            }
        }
    }

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            Log.d("WTF", "handleMessage UI Thread?= ${Looper.myLooper() == Looper.getMainLooper()}")
            Log.d("WTF", "Add...")
            Thread.sleep(3000)
            Log.d("WTF", "Add size=${msg.arg2} success")
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        Log.d("WTF", "onCreate")
        val handlerThread: HandlerThread =
            HandlerThread("StartService", android.os.Process.THREAD_PRIORITY_BACKGROUND)
        handlerThread.start()
        serviceLooper = handlerThread.looper
        serviceHandler = ServiceHandler(handlerThread.looper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("WTF", "onStartCommand")
        serviceHandler?.obtainMessage()?.also { message ->
            message.arg1 = startId
            intent?.let { intent ->
                message.arg2 = intent.getIntExtra("DATA", 0)
            }
            serviceHandler?.sendMessage(message)
        }
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("WTF", "onBind")
        return null
    }

    override fun onDestroy() {
        Log.d("WTF", "onDestroy")
    }
}