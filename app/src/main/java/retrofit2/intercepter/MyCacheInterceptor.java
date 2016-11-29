package retrofit2.intercepter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Aaron Wang on 2016/11/29.
 */
public class MyCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .removeHeader("Pragma") //这个头信息也会影响缓存, 排除干扰
                .header("Cache-Control", "public, max-age=" + 60) // 先统一设置缓存60s
                .build();
    }
}
