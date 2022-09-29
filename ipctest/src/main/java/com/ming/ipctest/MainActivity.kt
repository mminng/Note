package com.ming.ipctest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ming.ipctest.databinding.ActivityMainBinding
import com.ming.myipc.aidl.RemoteAidl

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.sendByMessenger.setOnClickListener {
            val message: Message = Message.obtain()
            message.arg1 = 110
            message.replyTo = client
            service?.send(message)
        }
        binding.sendByAidl.setOnClickListener {
//            binding.message.text = aidlService?.random.toString()
//            aidlService?.let {
//                val bundle: Bundle = it.bundle
//                bundle.classLoader = classLoader
//                val random: Int? = bundle.getParcelable<RemotePar>("DATA")?.random
//                binding.message.text = random.toString()
//            }
            binding.message.text = aidlService?.data?.random.toString()

        }
    }

    override fun onStart() {
        super.onStart()
        val intent: Intent = Intent()
        intent.component = ComponentName(
            "com.ming.myipc", "com.ming.myipc.messengerservice.MessengerService"
        )
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        Intent().also {
            it.component = ComponentName("com.ming.myipc", "com.ming.myipc.aidl.RemoteService")
            bindService(it, aidlServiceConnection, BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
        if (bound) {
            unbindService(serviceConnection)
        }
        if (aidlBound) {
            unbindService(aidlServiceConnection)
        }
    }

    private var service: Messenger? = null
    private var client: Messenger = Messenger(ServiceReceiverHandler())
    private var bound: Boolean = false

    private inner class ServiceReceiverHandler : Handler() {

        override fun handleMessage(msg: Message) {
            Log.d("WTF", "Client handleMessage:${msg.arg1}")
            binding.message.text = msg.arg1.toString()
        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, _service: IBinder?) {
            Log.d("WTF", "onServiceConnected:className=${name?.className}")
            Log.d("WTF", "onServiceConnected:package=${name?.packageName}")
            service = Messenger(_service)
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            bound = false
        }
    }

    private var aidlService: RemoteAidl? = null
    private var aidlBound: Boolean = false

    private val aidlServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, _service: IBinder?) {
            Log.d("WTF", "onServiceConnected aidl")
            aidlService = RemoteAidl.Stub.asInterface(_service)
            aidlBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            aidlService = null
            aidlBound = false
        }
    }
}