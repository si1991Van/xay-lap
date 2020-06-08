package com.viettel.construction.util;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.viettel.construction.R;
import com.viettel.construction.server.util.Logger;
import com.viettel.construction.common.VConstant;

/**
 * Created by Ramona on 3/8/2018.
 */

public class FileUtils {
    private static FileUtils instance;

    private FileUtils() {

    }

    public static FileUtils getInstance() {
        if (instance == null)
            return new FileUtils();
        return instance;
    }

    public static String getFileName() {
        String result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(VConstant.FORMAT_TIME_CURRENT);
            Date date = new Date();
            result = dateFormat.format(date);
        } catch (Exception e) {

        }
        return result;
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(Context context, String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use getExternalFilesDir on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    context.getPackageName());
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Logger.e("failed to create directory");
            }
            // Return the file target for the photo based on filename
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
            return file;
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
