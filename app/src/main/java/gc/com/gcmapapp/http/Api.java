package gc.com.gcmapapp.http;


import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import gc.com.gcmapapp.application.Constants;
import gc.com.gcmapapp.utils.SharePreferenceUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static ApiService SERVICE;

    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 60;

    public static ApiService getDefault(final Context context) {
//        if (SERVICE == null) {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        /**
         *  拦截器
         */


        final String phoneId = (String) SharePreferenceUtil.get(context, Constants.PHONE_ID, "");
        final String token = (String) SharePreferenceUtil.get(context, Constants.TOKEN, "");
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();


                HttpUrl.Builder authorizedUrlBuilder = request.url()
                        .newBuilder();
                //添加统一参数 如手机唯一标识符,token等
//                            .addQueryParameter("cTuiId","value1")
//                            .addQueryParameter("key2", "value2");


                Request newRequest = request.newBuilder()
                        //对所有请求添加请求头
                        .header("phoneId", phoneId)
                        .header("token", token)
                        .method(request.method(), request.body())
                        .url(authorizedUrlBuilder.build())
                        .build();
                return chain.proceed(newRequest);
            }
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(logging);

        SERVICE = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build().create(ApiService.class);
        //}
        return SERVICE;
    }


}
