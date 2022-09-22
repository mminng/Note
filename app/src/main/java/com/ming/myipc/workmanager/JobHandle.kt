package com.ming.myipc.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by zh on 2022/9/22.
 */
class JobHandle(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        Log.e("wtf", "doWork!!!")
        return Result.success()
    }
}