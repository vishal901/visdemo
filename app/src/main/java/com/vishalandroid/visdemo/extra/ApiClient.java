package com.vishalandroid.visdemo.extra;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vishal on 26/9/16.
 */
public class ApiClient {

    private static Retrofit retrofit = null;

    public static OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
 //           .addNetworkInterceptor(new StethoInterceptor())
            .connectTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient1)
                    .build();
        }
        return retrofit;
    }


    public static void showLog(String TAG, String msg) {
        Log.e(TAG, msg);
    }

}
