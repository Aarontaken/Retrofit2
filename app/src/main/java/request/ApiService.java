package request;

import java.util.Map;

import module.WeatherInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Aaron Wang on 2016/11/11.
 */
public interface ApiService {

    // example for simple get request
    @GET("adat/sk/101010100.html")
    Observable<WeatherInfo> getWeatherInfo();

    // example for download file
    @GET("uploads/item/{pic}")
    Observable<ResponseBody> downloadPic(@Path("pic") String path);

    // example for upload single file
    @Multipart
    @POST("v1/public/core/")
    Observable<ResponseBody> uploadPic(@Query("service") String param,
                                       @Part("description") RequestBody description,
                                       @Part MultipartBody.Part file);

    // example for upload multiple files
    @Multipart
    @POST("v1/public/core/")
    Observable<ResponseBody> uploadMultiPic(@Query("service") String param,
                                       @Part("description") RequestBody description,
                                       @PartMap Map<String ,RequestBody> files);
}
