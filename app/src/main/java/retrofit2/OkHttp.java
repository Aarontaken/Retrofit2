package retrofit2;

import com.aaron.me.retrofit2.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Aaron on 2016/11/11.
 */
public enum OkHttp {
    INSTANCE;
    private OkHttpClient okHttpClient;

    OkHttp() {
        init();
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 添加log信息拦截器
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(getLoggingInterceptor());
        }

        // 设置超时和错误重连
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Interceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
