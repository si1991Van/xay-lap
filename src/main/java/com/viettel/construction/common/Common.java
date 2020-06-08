package com.viettel.construction.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.viettel.construction.R;

/**
 * Created by BNO on 8/8/2018.
 */

public class Common {
    private static Common _instance = null;

    private Common() {
        //Do Nothing
    }
    private static synchronized Common getInstance() {
        if (_instance == null)
            _instance = new Common();
        return _instance;
    }

    public static void showToastError(Context context, String... msg) {
        Toast.makeText(context, R.string.msg_error, Toast.LENGTH_SHORT).show();
        if (msg != null && msg.length > 0)
            Log.d("ERROR", msg[0]);
    }
}
