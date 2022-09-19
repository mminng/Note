package com.ming.myipc.aidl

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by zh on 2022/9/19.
 */
@Parcelize
data class RemotePar(val random: Int) : Parcelable
