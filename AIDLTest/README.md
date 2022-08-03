AIDL 全称 Android Interface Definition Language ，安卓接口定义语言。  
AIDL 用来解决 Android 的跨进程通信问题，底层原理是 Binder ，实现思路是 C/S 架构思想。

* Server ：接收请求，提供处理逻辑，并发送响应数据。
* Client ：发起请求，接收响应数据。

##### C/S 之间通过 Binder 对象进行通信。  
Server 需要实现一个 Service 作为服务器，Client 侧则需要调用发起请求的能力。  
Client 需要调用 bindService 绑定到远程服务，然后通过 ServiceConnection 来接收远程服务的 Binder 对象。拿到 Binder 对象后就可以调用远程服务中定义的方法了。
  
在 AIDL 文件中需要明确标明引用到的数据类型所在的包名，即使两个文件处在同个包名下。

因为是跨进程通信，所以需要实现序列化，AIDL 专门为 Android 设计，所以它的序列化不能使用 Java 提供的 Serializable ，而是 Android 提供的 Parcelable 接口。  

##### AIDL 支持的数据类型：

* 八种基本数据类型：byte、char、short、int、long、float、double、boolean
String，CharSequence
* List类型。List承载的数据必须是AIDL支持的类型，或者是其它声明的AIDL对象
* Map类型。Map承载的数据必须是AIDL支持的类型，或者是其它声明的AIDL对象

demo：

#### 服务端结构：
![服务端结构](https://github.com/ruoxuanFu/AndroidTips/blob/main/AIDLTest/images/Service%E7%BB%93%E6%9E%84%E7%9B%AE%E5%BD%95.png)

##### 定义通信类

	public class Msg implements Parcelable {
	    private String msg;
	    private long time;
	
	    public String getMsg() {
	        return msg;
	    }
	
	    public void setMsg(String msg) {
	        this.msg = msg;
	    }
	
	    public long getTime() {
	        return time;
	    }
	
	    public void setTime(long time) {
	        this.time = time;
	    }
	
	    protected Msg(Parcel in) {
	        msg = in.readString();
	        time = in.readLong();
	    }
	
	    public Msg() {
	    }
	
	    public static final Creator<Msg> CREATOR = new Creator<Msg>() {
	        @Override
	        public Msg createFromParcel(Parcel in) {
	            return new Msg(in);
	        }
	
	        @Override
	        public Msg[] newArray(int size) {
	            return new Msg[size];
	        }
	    };
	
	    @Override
	    public int describeContents() {
	        return 0;
	    }
	
	    @Override
	    public void writeToParcel(Parcel parcel, int i) {
	        parcel.writeString(msg);
	        parcel.writeLong(time);
	    }
	
	    @Override
	    public String toString() {
	        return "Msg{" +
	                "msg='" + msg + '\'' +
	                ", time=" + time +
	                '}';
	    }
	}

在服务端aidl的相同目录下创建Msg.aidl文件，并对这个类型进行声明

	package com.fdd.aidltestservice.beans;

	parcelable Msg;

##### 定义服务端向客户端发送信息接口：IReceiveMsgListener

	interface IReceiveMsgListener {
    	void onReceive(in Msg msg);
	}

##### 定义服务端提供的功能接口：IMsgManager

	interface IMsgManager {
	
	    // 发消息
	    void sendMsg(in Msg msg);
	
	    // 客户端注册监听回调
	    void registerReceiveListener(in IReceiveMsgListener listener);
	
	    // 客户端取消监听回调
	    void unregisterReceiveListener(in IReceiveMsgListener listener);
	}

AIDL 文件创建完成，需要手动build项目

##### 定义服务端
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

在服务端的AndroidManifest.xml中声明：

	<service
	    android:name=".MyService"
	    android:enabled="true"
	    android:exported="true">
	    <intent-filter>
	        <!--用于其他进程隐式启动-->
	        <action android:name="com.fdd.aidltestservice.MyService" />
	    </intent-filter>
	</service>

服务端创建完成

#### 创建客户端
首先需要创建AIDL文件，可以直接把服务端的文件复制过来，注意包名依旧是服务端包名，使用到的对象包名亦然

##### 在客户端绑定服务端服务即可
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