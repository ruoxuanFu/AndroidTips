package com.hc.myapplication.utils.optimization.task;

/**
 * 主线程任务
 *
 * @author furuoxuan
 */
public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
