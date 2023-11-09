package com.simplecity.amp_library.utils;

import android.support.annotation.Nullable;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.simplecity.amp_library.BuildConfig;
import android.os.Environment
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class LogUtils {

    private LogUtils() {
        //no instance
    }

    public static void logException(String tag, String message, @Nullable Throwable throwable) {
       writerLog(true,message);
        if (BuildConfig.DEBUG) {
            Log.e(tag, message + "\nThrowable: " + (throwable != null ? throwable.getMessage() : null));
            if (throwable != null) {
                throwable.printStackTrace();
            }
        } else {
            Crashlytics.log(Log.ERROR, tag, message + "\nThrowable: " + (throwable != null ? throwable.getMessage() : null));
            Crashlytics.logException(throwable);
        }
    }



    //  private static final int LEVEL_FILE = 0x2;

    /**
     * 路径 "/storage/emulated/0/lowTemperatureTest"
     * @param msg 需要打印的内容
     */
    public static void writerLog(boolean isWriteLog, String msg) {
       
        if (isWriteLog) {
            //保存到的文件路径
            final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileWriter fileWriter;
            BufferedWriter bufferedWriter = null;

            try {
                //创建文件夹
                File dir = new File(filePath, "shuttlePlus");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //创建文件
                File file = new File(dir, "log.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                //写入日志文件
                fileWriter = new FileWriter(file, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write( msg + "=======时间 :"+ getCurrentTime()+ "\n");
                bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } else {
            Log.d("lowTemperature", msg+"");
        }


    }
    private static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        //@SuppressLint("SimpleDateFormat")
         SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(calendar.getTime());
    } 
}