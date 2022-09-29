package com.ming.myipc.workmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture

/**
 * Created by zh on 2022/9/22.
 */
class JobHandle(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        setProgressAsync(Data.Builder().putInt("Progress", 0).build())
        Log.e("wtf", "work progress...")
        Thread.sleep(5000)
        setProgressAsync(Data.Builder().putInt("Progress", 100).build())
        Log.e("wtf", "work done!")
        return Result.success()
    }

    override fun getForegroundInfoAsync(): ListenableFuture<ForegroundInfo> {
        return super.getForegroundInfoAsync()
    }
}