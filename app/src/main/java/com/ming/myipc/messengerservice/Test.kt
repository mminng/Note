package com.ming.myipc.messengerservice

import android.os.Bundle
import android.util.Log
import com.ming.myipc.aidl.RemoteAidl
import com.ming.myipc.aidl.RemotePar
import kotlin.random.Random

/**
 * Created by zh on 2022/9/29.
 */
class Test : RemoteAidl.Stub() {
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