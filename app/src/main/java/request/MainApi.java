package request;

import module.WeatherInfo;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chunyu on 2016/11/11.
 */
public interface MainApi {

    @GET("adat/sk/101010100.html")
    Call<WeatherInfo> getWeatherInfo();
}
