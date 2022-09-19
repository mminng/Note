package com.ming.myipc.messengerservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import kotlin.random.Random

/**
 * Created by zh on 2022/9/16.
 */
class MessengerService : Service() {

    private lateinit var messenger: Messenger

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("WTF", "onBind")
        messenger = Messenger(ClientReceiverHandler(this))
        return messenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("WTF", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("WTF", "onDestroy")
    }

    internal class ClientReceiverHandler(context: Context) : Handler() {

        override fun handleMessage(msg: Message) {
            Log.d("WTF", "Service handleMessage:${msg.arg1}")
            val message: Message = obtainMessage()
            message.arg1 = Random.nextInt(100)
            msg.replyTo.send(message)
        }
    }
}