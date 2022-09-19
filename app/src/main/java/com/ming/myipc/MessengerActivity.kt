package com.ming.myipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ming.myipc.databinding.ActivityMessengerBinding
import com.ming.myipc.messengerservice.MessengerService

/**
 * Created by zh on 2022/9/16.
 */
class MessengerActivity : AppCompatActivity() {

    private val binding: ActivityMessengerBinding by lazy {
        ActivityMessengerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.send.setOnClickListener {
            if (bound) {
                val message: Message = Message.obtain()
                message.arg1 = 110
                message.replyTo = client
                service?.send(message)
            }
        }
    }

    private inner class ServiceReceiverHandler : Handler() {

        override fun handleMessage(msg: Message) {
            Log.d("WTF", "Client handleMessage:${msg.arg1}")
            binding.message.text = msg.arg1.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this@MessengerActivity, MessengerService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            unbindService(serviceConnection)
        }
    }

    private var service: Messenger? = null
    private var client: Messenger = Messenger(ServiceReceiverHandler())
    private var bound: Boolean = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, _service: IBinder?) {
            service = Messenger(_service)
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            bound = false
        }
    }
}