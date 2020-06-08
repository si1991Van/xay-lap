package com.viettel.construction.util;

import com.viettel.construction.server.util.StringUtil;

import com.viettel.construction.common.ErrorMessage;

/**
 * Created by Manroid on 15/01/2018.
 */

public class ValidationUtil implements ErrorMessage{


    public static String validateAccount(String acc) {
        if (StringUtil.isNullOrEmpty(acc))
            return E001;
        return I001;
    }

}
