package com.fdd.aidltestservice;

import com.fdd.aidltestservice.beans.Msg;

interface IReceiveMsgListener {

    void onReceive(in Msg msg);
}