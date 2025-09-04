package com.rp.uihelpher.onShare;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.mm.uihelper.R;

import java.io.File;
import java.io.FileOutputStream;

import log.IsLog;

public class ScreenshotUtils {

    public static File getMainDirectoryName(Context context) {
        File mainDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), context.getResources().getString(R.string.app_name));
        //If File is not present create directory
        if (!mainDir.exists()) {
            if (mainDir.mkdir())
                new IsLog("Create Directory", "Main Directory Created : " + mainDir);
        }
        return mainDir;
    }

    public static File  store(Bitmap bm, String fileName, File saveFilePath) {
        File dir = new File(saveFilePath.getAbsolutePath());
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
