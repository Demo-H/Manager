package com.dhunter.system.baseutil;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class SystemUtil {
    private static int CpuCoresNum =0;
    private static class CpuFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            // TODO Auto-generated method stub
            if(Pattern.matches("cpu[0-9]", pathname.getName()))
                return true;
            return false;
        }

    }

    public static int getCpuCoresNum()
    {
        if(CpuCoresNum == 0)
        {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            CpuCoresNum = files.length;
        }
        return CpuCoresNum;
    }



    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader br = new BufferedReader(new FileReader(file));
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> rapis = am.getRunningAppProcesses();
        if(rapis == null){
            rapis = am.getRunningAppProcesses();
        }
        if(rapis!=null) {
            for (ActivityManager.RunningAppProcessInfo rapi : rapis) {
                if(rapi.pid == android.os.Process.myPid()){
                    return rapi.processName;
                }
            }
        }
        return null;
    }

    public  static boolean isProcessFront(Context context,String processName)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> rapis = am.getRunningAppProcesses();
        if(rapis == null){
            rapis = am.getRunningAppProcesses();
        }
        if(rapis!=null) {
            for (ActivityManager.RunningAppProcessInfo rapi : rapis) {
                if (processName.equals(rapi.processName)) {
                    if (rapi.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
