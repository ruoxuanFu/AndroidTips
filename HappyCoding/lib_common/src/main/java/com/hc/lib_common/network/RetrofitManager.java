package com.hc.lib_common.network;

import com.hc.lib_common.network.ApiService.TestService;
import com.hc.lib_common.network.interceptors.DomainSwitchInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author furuoxuan
 */
public class RetrofitManager {

    private Retrofit mRetrofit;

    private RetrofitManager() {
    }

    private static class SingletonInstance {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void init(String host) {
        if (mRetrofit == null) {
            //添加日志拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS).
                    readTimeout(Config.READ_TIMEOUT, TimeUnit.MILLISECONDS).
                    writeTimeout(Config.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpBuilder.addInterceptor(logging);
            //添加切换host拦截器
            okHttpBuilder.addInterceptor(new DomainSwitchInterceptor());

            //添加socketFactory
            SSLContext sslContext = SslContextUtil.getDefaultSslContext();
            if (sslContext != null) {
                SSLSocketFactory socketFactory = sslContext.getSocketFactory();
                okHttpBuilder.sslSocketFactory(socketFactory, SslContextUtil.TRUST_MANAGER);
            }
            //添加域名验证器
            okHttpBuilder.hostnameVerifier(SslContextUtil.HOSTNAME_VERIFIER);

            //创建client
            OkHttpClient okHttpClient = okHttpBuilder.build();
            //创建Retrofit
            mRetrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(host)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public TestService getTestService() {
        if (mRetrofit == null) {
            throw new NullPointerException("Retrofit hasn't initialized yet!");
        }
        return mRetrofit.create(TestService.class);
    }
}