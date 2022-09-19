package com.ming.myipc.bindservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import kotlin.random.Random

/**
 * Created by zh on 2022/9/16.
 */
class BindService : Service() {

    private val binder: ServiceBinder = ServiceBinder()

    inner class ServiceBinder : Binder() {

        fun getService(): BindService = this@BindService
    }

    override fun onCreate() {
        Log.d("WTF", "onCreate")
    }

    override fun onBind(p0: Intent?): IBinder {
        Log.d("WTF", "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("WTF", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        Log.d("WTF", "onDestroy")
    }

    private val handler: Handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            count++
            _loop?.invoke(count.toString())
            handler.postDelayed(this, 1000)
        }
    }
    private var count: Int = 0
    private var _loop: ((count: String) -> Unit)? = null

    fun registerCountListener(listener: (count: String) -> Unit) {
        _loop = listener
        handler.postDelayed(runnable, 1000)
    }

    fun getRandom(): String = Random.nextInt(100).toString()
}