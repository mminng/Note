// RemoteAidl.aidl
package com.ming.myipc.aidl;

// Declare any non-default types here with import statements
import com.ming.myipc.aidl.RemotePar;

interface RemoteAidl {

    int getRandom();

    RemotePar getData();

    Bundle getBundle();
}