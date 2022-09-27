package com.hc.myapplication.utils.optimization;

import android.os.Looper;
import android.os.MessageQueue;

import com.hc.myapplication.utils.optimization.task.DispatchRunnable;
import com.hc.myapplication.utils.optimization.task.Task;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 延迟初始化方案
 * 适合耗时较少的任务
 * 核心思想：对延迟的任务分批初始化
 * IdleHandler封装类，利用IdleHandler的特性，空闲执行
 * <p>
 * 用法：
 * DelayInitDispatcher delayInitDispatcher = new DelayInitDispatcher();
 * delayInitDispatcher.addTask(new DelayInitTaskA())
 * .addTask(new DelayInitTaskB())
 * .start();
 * <p>
 * 优点：执行时机明确，解决界面卡顿
 *
 * 由于 queueIdle() 运行的线程，只和当前 MessageQueue 的 Looper 所在的线程有关，子线程一样可以构造 Looper，并添加 IdleHandler；
 * 因此在主线程中运行时耗时不能超过10毫秒，否则会阻塞主线程运行
 *
 * @author furuoxuan
 */
public class DelayInitDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = () -> {
        if (mDelayTasks.size() > 0) {
            Task task = mDelayTasks.poll();
            new DispatchRunnable(task).run();
        }
        return !mDelayTasks.isEmpty();
    };

    public DelayInitDispatcher addTask(Task task) {
        mDelayTasks.add(task);
        return this;
    }

    public void start() {
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
