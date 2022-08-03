package com.fdd.aidltestclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.aidltestservice.IMsgManager;
import com.fdd.aidltestservice.IReceiveMsgListener;
import com.fdd.aidltestservice.beans.Msg;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bind = findViewById(R.id.bind);
        Button btn1 = findViewById(R.id.btn1);

        bind.setOnClickListener(view -> {
            // 隐式启动Service
            Intent intent = new Intent();
            intent.setAction("com.fdd.aidltestservice.MyService");
            intent.setPackage("com.fdd.aidltestservice");
            bindService(intent, connection, BIND_AUTO_CREATE);
        });

        btn1.setOnClickListener(view -> {
            Msg msg1 = new Msg();
            msg1.setMsg("客户端请求 ${Date(System.currentTimeMillis())}\n ${packageName}");
            msg1.setTime(System.currentTimeMillis());
            try {
                iMsgManager.sendMsg(msg1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (iMsgManager != null && iMsgManager.asBinder() != null && iMsgManager.asBinder().isBinderAlive()) {
            try {
                iMsgManager.unregisterReceiveListener(iReceiveMsgListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
        super.onDestroy();
    }

    private IMsgManager iMsgManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMsgManager = IMsgManager.Stub.asInterface(iBinder);
            if (iMsgManager != null) {
                try {
                    iMsgManager.asBinder().linkToDeath(deathRecipient, 0);
                    iMsgManager.registerReceiveListener(iReceiveMsgListener);
                    Log.d("fmsg", "onServiceConnected success");
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.d("fmsg", "onServiceConnected false");
                }
            } else {
                Log.d("fmsg", "onServiceConnected false");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iMsgManager != null) {
                iMsgManager.asBinder().unlinkToDeath(this, 0);
                iMsgManager = null;
            }
        }
    };

    private IReceiveMsgListener iReceiveMsgListener = new IReceiveMsgListener.Stub() {
        @Override
        public void onReceive(Msg msg) throws RemoteException {
            Log.d("fmsg", "iReceiveMsgListener onReceive: " + msg.toString());
        }
    };
}