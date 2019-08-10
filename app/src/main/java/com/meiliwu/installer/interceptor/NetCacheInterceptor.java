package com.meiliwu.installer.interceptor;



import com.meiliwu.installer.utils.NetWorkUtil;
import com.meiliwu.installer.utils.StringUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author Chaney
 * Date   2017/3/6 下午9:43.
 * EMail  qiuzhenhuan.hi@gmail.com
 */

public class NetCacheInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        String maxAge = request.header("Cache-Control");

        Response.Builder respBuilder = response.newBuilder();
        respBuilder.removeHeader("Pragma").removeHeader("Cache-Control");

        if(NetWorkUtil.isNetConnected()){
            if(!StringUtil.isEmpty(maxAge)){
                respBuilder.header("Cache-Control", "max-age=" + maxAge);
            }else {
                respBuilder.header("Cache-Control", "max-age=" + 60 * 60 * 3);
            }
        }else {
            respBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7);
        }

        return respBuilder.build();
    }

}
