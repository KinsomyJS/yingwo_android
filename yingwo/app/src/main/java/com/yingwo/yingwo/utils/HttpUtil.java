package com.yingwo.yingwo.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by FJS0420 on 2016/8/13.
 */

public class HttpUtil {
    public static String getToken(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        String postBody = "Hello yingwo";

        Request request = new Request.Builder()
                .url(url)
                .header("X-Requested-With","XMLHttpRequest")
                .post(RequestBody.create(null,postBody))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

        return response.body().string();
    }
}
