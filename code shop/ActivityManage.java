package com.sflep.course.util;

import android.app.Activity;

import java.util.Stack;

/**
 * 管理activity的manage
 *
 * @author furuoxuan
 */
public class ActivityManage {

    private final Stack<Activity> activityStack = new Stack();

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 获取activity栈
     *
     * @return
     */
    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 只是移除栈，不用结束Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     *
     * @return
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束当前页面之前的全部页面
     */
    public void finishBeforeActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity != null) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}