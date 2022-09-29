package com.ming.myipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.ming.myipc.bindservice.BindService
import com.ming.myipc.databinding.ActivityMainBinding
import com.ming.myipc.startservice.StartIntentService
import com.ming.myipc.startservice.StartService
import com.ming.myipc.workmanager.JobHandle

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val range: IntRange = 1..10
        binding.startIntentService.setOnClickListener {
            if (range.random() > 5) {
                StartIntentService.start(
                    this@MainActivity, StartIntentService.TASK_ADD, "Student Jack"
                )
            } else {
                StartIntentService.start(
                    this@MainActivity, StartIntentService.TASK_REMOVE, "Student Mary"
                )
            }
        }
        binding.startService.setOnClickListener {
            StartService.start(this@MainActivity, range.random())
        }
        binding.bindService.setOnClickListener {
            Intent(this@MainActivity, BindService::class.java).also { intent ->
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }
        binding.unbindService.setOnClickListener {
            if (bound) {
                unbindService(serviceConnection)
            }
            bound = false
        }
        binding.toMessenger.setOnClickListener {
            Intent(this@MainActivity, MessengerActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.toAidl.setOnClickListener {
            Intent().also {
                it.setClass(this@MainActivity, AidlActivity::class.java)
                startActivity(it)
            }
        }
        binding.work.setOnClickListener {
            val work: WorkRequest = OneTimeWorkRequestBuilder<JobHandle>()
//                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(this@MainActivity)
                .getWorkInfoByIdLiveData(work.id)
                .observe(this, Observer {
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        Log.i("wtf", "success!")
                    }
                })
            WorkManager.getInstance(this@MainActivity)
                .enqueue(work)
        }
    }

    private lateinit var bindService: BindService
    private var bound: Boolean = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            Log.d("WTF", "onServiceConnected")
            val binder: BindService.ServiceBinder = service as BindService.ServiceBinder
            bindService = binder.getService()
            bindService.registerCountListener {
                binding.countText.text = it
            }
            bound = true
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            Log.d("WTF", "onServiceDisconnected")
            bound = false
        }
    }
}