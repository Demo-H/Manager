package com.tupperware.mgt.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/2/8.
 */

public class ActivityManager {

    private static ActivityManager mInstance;

    public static synchronized ActivityManager getInstance(){
        if(mInstance == null){
            mInstance = new ActivityManager();
        }
        return mInstance;
    }

    //单进程使用
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    /**
     * 保存Activity到现有列表中
     * @param activity
     */
    public void addActivity(Activity activity) {

        synchronized (activityList) {
            //防止启动activity'的时候，那种连续点击，导致连续启动了两次的情况，比如某页面点击图片跳到大图页面，
            // 这里可能也会有个隐患，比如说activity自己启动自己就会被这里给过滤掉了，不过目前在app没看到有这种业务，而且这种也算很特殊，一般很难会有这种情况
            if(activityList.size()>0 && getKey(activity).equals(getKey(activityList.get(activityList.size()-1)))){
                Activity  topActivity = activityList.get(activityList.size()-1);
                activityList.remove(topActivity);
                topActivity.finish();
            }
            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        synchronized (activityList) {
            activityList.remove(activity);
        }
    }

    /**
     * 关闭保存的所有Activity
     */
    public void exit() {

        synchronized (activityList) {
            for(Activity activity:activityList) {
                activity.finish();
            }
            activityList.clear();
        }
    }

    private String getKey(Activity activity) {
        String key = activity.getClass().getName();
        return key;
    }

    public void removeAll() {
        activityList.clear();
    }

    //多进程使用

    /*private static ActivityManager mInstance;
    private Application mApplication;
    private ArrayList<Application.ActivityLifecycleCallbacks> mActivityListCallbacks = new ArrayList<Application.ActivityLifecycleCallbacks>();
    private ArrayList<Activity> mActivitys = new ArrayList<Activity>(); //保存当前进程所有的activity引用是为了当进程退出的时候，finish掉所有当前进程的activity
    private boolean mNeedSaveActivitys = false; //是否需要把当前进程的activity保存到mActivitys，默认不需要



    public static synchronized ActivityManager getInstance(){
        if(mInstance == null){
            mInstance = new ActivityManager();
        }
        return mInstance;
    }

    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks  lifecycleCallback){
        synchronized (mActivityListCallbacks){
            mActivityListCallbacks.add(lifecycleCallback);
        }
    }

    public void unRegisterActivityLifecycleCallback(Application.ActivityLifecycleCallbacks  lifecycleCallback ){
        synchronized (mActivityListCallbacks){
            mActivityListCallbacks.remove(lifecycleCallback);
        }
    }

    public void initate(Application application) {
        mApplication = application;
        mApplication.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback: mActivityListCallbacks){
                callback.onActivityCreated(activity,savedInstanceState);
            }
        }

        pushActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback: mActivityListCallbacks){
                callback.onActivityStarted(activity);
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback:mActivityListCallbacks){
                callback.onActivityResumed(activity);
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback:mActivityListCallbacks){
                callback.onActivityPaused(activity);
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback:mActivityListCallbacks){
                callback.onActivityStopped(activity);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback:mActivityListCallbacks){
                callback.onActivitySaveInstanceState(activity,outState);
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        synchronized (mActivityListCallbacks){
            for(Application.ActivityLifecycleCallbacks  callback:mActivityListCallbacks){
                callback.onActivityDestroyed(activity);
            }
        }

        popActvity(activity);
    }

    public void needSaveActivitys(boolean need){
        mNeedSaveActivitys = need;
    }

    private  String getKey(Activity activity) {
        String key = activity.getClass().getName();
        return key;
    }

    public void pushActivity(Activity activity){
        if(!mNeedSaveActivitys)
            return;
        synchronized (mActivitys) {
            //防止启动activity'的时候，那种连续点击，导致连续启动了两次的情况，比如聊天页面点击图片跳到大图页面，
            // 这里可能也会有个隐患，比如说activity自己启动自己就会被这里给过滤掉了，不过目前在密友app没看到有这种业务，而且这种也算很特殊，一般很难会有这种情况
            if(mActivitys.size()>0 && getKey(activity).equals(getKey(mActivitys.get(mActivitys.size()-1)))){
                Activity  topActivity = mActivitys.get(mActivitys.size()-1);
                mActivitys.remove(topActivity);
                topActivity.finish();
            }
            mActivitys.add(activity);
        }
    }

    public  void popActvity(Activity activity){
        if(!mNeedSaveActivitys)
            return;
        synchronized (mActivitys) {
            mActivitys.remove(activity);
        }
    }

    public void finishProcessAllActivitys(){
        if(!mNeedSaveActivitys)
            return;
        synchronized (mActivitys) {
            for(Activity activity:mActivitys) {
                activity.finish();
            }
            mActivitys.clear();
        }
    }

    public void killSelfProcess(){
        finishProcessAllActivitys();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void addActivity(Activity activity) {

    }*/

}
