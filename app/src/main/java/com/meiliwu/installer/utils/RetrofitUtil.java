package com.meiliwu.installer.utils;

import android.os.Environment;

import com.meiliwu.installer.constant.Constant;
import com.meiliwu.installer.interceptor.LocalCacheInterceptor;
import com.meiliwu.installer.interceptor.NetCacheInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:  ZhangTao
 * Date: 2018/3/5.
 */

public class RetrofitUtil {
    private static volatile RetrofitUtil instance;

    /*缓存最大容量为10M*/
    public static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new LocalCacheInterceptor())
                .addInterceptor(new NetCacheInterceptor())
                .cache(new Cache(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), MAX_CACHE_SIZE))
                .build();
        return okHttpClient;
    }


    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }


    public <S> S createService(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }
}
