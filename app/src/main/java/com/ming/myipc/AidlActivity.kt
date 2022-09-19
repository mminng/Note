package com.ming.myipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ming.myipc.aidl.RemoteAidl
import com.ming.myipc.aidl.RemotePar
import com.ming.myipc.aidl.RemoteService
import com.ming.myipc.databinding.ActivityAidlBinding
import com.ming.myipc.databinding.ActivityMessengerBinding
import com.ming.myipc.messengerservice.MessengerService

/**
 * Created by zh on 2022/9/16.
 */
class AidlActivity : AppCompatActivity() {

    private val binding: ActivityAidlBinding by lazy {
        ActivityAidlBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.sendAidl.setOnClickListener {
            if (bound) {
//                binding.messageAidl.text = service?.random.toString()
//                binding.messageAidl.text = service?.data?.random.toString()
                service?.let {
                    val bundle: Bundle = it.bundle
                    bundle.classLoader = classLoader
                    val random: Int? = bundle.getParcelable<RemotePar>("DATA")?.random
                    binding.messageAidl.text = random.toString()
                }
            }
        }
    }

    private inner class ServiceReceiverHandler : Handler() {

        override fun handleMessage(msg: Message) {
            Log.d("WTF", "Client handleMessage:${msg.arg1}")
            binding.messageAidl.text = msg.arg1.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this@AidlActivity, RemoteService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            unbindService(serviceConnection)
        }
    }

    private var service: RemoteAidl? = null
    private var bound: Boolean = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, _service: IBinder?) {
            Log.d("WTF", "onServiceConnected")
            service = RemoteAidl.Stub.asInterface(_service)
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            bound = false
        }
    }
}