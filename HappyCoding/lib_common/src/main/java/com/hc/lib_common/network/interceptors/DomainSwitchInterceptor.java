package com.hc.lib_common.network.interceptors;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 域名切换拦截器
 * 接口类对应方法添加如下注解:
 * eg:@Headers({"domain: http://xx.xx.xx"})
 * 或@Header("domain","http://xx.xx.xx");
 *
 * @author furuoxuan
 */
public class DomainSwitchInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(buildRequest(chain));
    }

    /**
     * 构建新的服务器地址
     *
     * @param chain Chain
     * @return Request
     */
    public Request buildRequest(Chain chain) {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        String domain = request.header("domain");

        if (!TextUtils.isEmpty(domain)) {
            //替换服务器
            HttpUrl domainUrl = HttpUrl.parse(domain);

            HttpUrl newUrl = httpUrl.newBuilder()
                    .scheme(domainUrl.scheme())
                    .host(domainUrl.host())
                    .port(domainUrl.port())
                    .build();

            //构建新的请求方式
            Request.Builder builder = request.newBuilder();
            builder.url(newUrl);
            request = builder.build();
        }
        return request;
    }

}
