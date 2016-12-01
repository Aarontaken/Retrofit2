package retrofit2.intercepter;

import android.util.Log;

import com.aaron.me.retrofit2.AppApplication;
import com.aaron.me.retrofit2.AppUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aaron Wang on 2016/11/29.
 */
public class MyCacheInterceptor implements Interceptor {

    /**
     * 请求时:没网时强制使用缓存, 有缓存就使用,没有会报504 Unsatisfiable Request错误
     * 响应头处理:有网时, 每次都请求服务器, 没网时设置缓存4周
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!AppUtil.networkIsAvailable(AppApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (AppUtil.networkIsAvailable(AppApplication.getContext())) {
            int maxAge = 0;
            // 有网络时 设置缓存超时时间0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma") //这个头信息也会影响缓存, 排除干扰
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma") //这个头信息也会影响缓存, 排除干扰
                    .build();
        }
        // 打印log查看数据来源(网络or缓存)
        Log.e("interceptor","cacheResponse:"+ response.cacheResponse() + "\n" +
                            "networkResponse:" + response.networkResponse());
        return response;
    }
}
