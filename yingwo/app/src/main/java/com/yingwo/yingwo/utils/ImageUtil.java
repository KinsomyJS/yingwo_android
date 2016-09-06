package com.yingwo.yingwo.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by wangyu on 9/6/16.
 */

public class ImageUtil {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


}
