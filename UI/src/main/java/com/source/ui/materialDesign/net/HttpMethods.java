package com.source.ui.materialDesign.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class HttpMethods {
    private static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 10;
    private MovieService movieService;

    private HttpMethods() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        //设置超时时间，并且设置时间的单位
        okBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //设置okhttp的缓存的机制
                .addNetworkInterceptor(new StethoInterceptor());
//                .cache(cache);

        //进行retrofit的初始化操作
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okBuilder.build())
                .build();

        //初始化接口方法中的实例
        movieService = retrofit.create(MovieService.class);
    }

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public MovieService getService() {
        return movieService;
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
