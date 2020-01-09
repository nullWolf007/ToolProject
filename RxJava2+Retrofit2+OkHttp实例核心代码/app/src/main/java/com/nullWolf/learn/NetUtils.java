package com.nullWolf.learn;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/9
 */
public class NetUtils {
    private static Retrofit commonRetrofit = null;

    public static Retrofit getNormalRetrofit() {
        //对于baseUrl相同的Retrofit应该复用Retrofit
        if (commonRetrofit == null) {
            commonRetrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .build();
        }
        return commonRetrofit;
    }

    /**
     * 返回一个普通的OkHttpClient
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        builder.connectTimeout(9, TimeUnit.SECONDS);//连接时间
        builder.readTimeout(10, TimeUnit.SECONDS);//数据传输时间
        builder.connectionPool(new ConnectionPool(5, 30, TimeUnit.SECONDS));//连接池
        builder.addInterceptor(httpLoggingInterceptor);//添加拦截器 用来打印日志

        return builder.build();
    }
}
