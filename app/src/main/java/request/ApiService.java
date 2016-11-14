package request;

import module.WeatherInfo;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aaron Wang on 2016/11/11.
 */
public interface ApiService {

    @GET("adat/sk/101010100.html")
    Observable<WeatherInfo> getWeatherInfo();

    @GET("uploads/item/{pic}")
    Observable<ResponseBody> downloadPic(@Path("pic") String path);
}
