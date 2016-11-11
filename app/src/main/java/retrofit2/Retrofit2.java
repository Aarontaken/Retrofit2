package retrofit2;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aaron on 2016/11/11.
 */
public enum Retrofit2 {
    // 可以创建多个INSTANCE,使用不同的baseUrl
    INSTANCE("http://www.weather.com.cn/");

    private Retrofit retrofit;

    Retrofit2(String baseUrl) {
        init(baseUrl);
    }

    private void init(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttp.INSTANCE.getOkHttpClient())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
