package com.fdd.aidltestservice;

import com.fdd.aidltestservice.beans.Msg;
import com.fdd.aidltestservice.IReceiveMsgListener;

interface IMsgManager {

    // 发消息
    void sendMsg(in Msg msg);

    // 客户端注册监听回调
    void registerReceiveListener(in IReceiveMsgListener listener);

    // 客户端取消监听回调
    void unregisterReceiveListener(in IReceiveMsgListener listener);
}