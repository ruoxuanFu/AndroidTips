package com.fdd.aidltestservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.fdd.aidltestservice.beans.Msg;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    static class MyBinder extends IMsgManager.Stub {
        RemoteCallbackList<IReceiveMsgListener> remoteCallbackList = new RemoteCallbackList<>();

        @Override
        public void sendMsg(Msg msg) throws RemoteException {
            Log.d("fmsg", "MyBinder sendMsg: " + msg.toString());
            int num = remoteCallbackList.beginBroadcast();
            for (int i = 0; i < num; i++) {
                IReceiveMsgListener listener = remoteCallbackList.getBroadcastItem(i);
                if (listener != null) {
                    Msg msg1 = new Msg();
                    msg1.setMsg("服务器响应 ${Date(System.currentTimeMillis())}\n ${packageName}");
                    msg1.setTime(System.currentTimeMillis());
                    listener.onReceive(msg1);
                }
            }
            remoteCallbackList.finishBroadcast();
        }

        @Override
        public void registerReceiveListener(IReceiveMsgListener listener) throws RemoteException {
            remoteCallbackList.register(listener);
        }

        @Override
        public void unregisterReceiveListener(IReceiveMsgListener listener) throws RemoteException {
            remoteCallbackList.unregister(listener);
        }
    }
}