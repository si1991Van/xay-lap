package com.viettel.construction.server.util;

import android.util.Log;

public class Logger {

    private static final String LOGGER_NAME = "Antsoft_Mobile";

    public static void d(String log) {
        Log.d(LOGGER_NAME, log);
    }

    public static void w(String log) {
        Log.w(LOGGER_NAME, log);
    }

    public static void e(String log) {
    }
}
