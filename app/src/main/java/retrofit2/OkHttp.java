package retrofit2;

import com.aaron.me.retrofit2.AppApplication;
import com.aaron.me.retrofit2.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Aaron Wang on 2016/11/11.
 */
public enum OkHttp {
    INSTANCE;
    private static final long CACHE_SIZE = 10 * 1024 * 1024;
    private OkHttpClient okHttpClient;

    OkHttp() {
        init();
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 添加log信息拦截器
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(getLoggingInterceptor());
        }

        // 设置超时和错误重连
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        // 设置缓存目录
        File cacheFile = new File(AppApplication.getContext().getCacheDir(), "cacheDir");
        builder.cache(new Cache(cacheFile, CACHE_SIZE));

        // 统一设置缓存, 加上这个对于服务器没有返回缓存响应头信息的数据也能进行离线查看, 对于有缓存响应头的数据不影响, 仍由服务器控制缓存时间
        // 按理来说缓存应该完全由服务器返回的header来控制, 若服务器不加控制才会采用这种拦截器方式!
//        builder.addInterceptor(new MyCacheInterceptor());

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
