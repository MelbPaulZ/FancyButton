package org.puzhao.awesomebutton.log;

import android.util.Log;

/**
 * Created by puzhao on 24/8/17.
 */

public class LogUtil {
    private static boolean debug = true;
    public static void Log(String tag, String logString){
        if (debug) Log.i(tag, "Log: " + logString);
    }

    public static <T> void Log(String tag, T data){
        Log(tag, String.valueOf(data));
    }
}
