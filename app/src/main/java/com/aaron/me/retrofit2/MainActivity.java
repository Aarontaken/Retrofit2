package com.aaron.me.retrofit2;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import module.WeatherInfo;
import okhttp3.ResponseBody;
import request.ApiService;
import request.fileRequest.FileDownloadHelper;
import retrofit2.Retrofit2;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.request)
    Button request;
    @InjectView(R.id.info)
    TextView info;
    @InjectView(R.id.download)
    Button download;
    @InjectView(R.id.image)
    ImageView image;
    private ApiService api;
    private ApiService downloadApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        api = Retrofit2.INSTANCE.getRetrofit().create(ApiService.class);
        downloadApi = Retrofit2.PICINSTANCE.getRetrofit().create(ApiService.class);
    }

    @OnClick(R.id.request)
    public void onWeatherClick() {
        Observable<WeatherInfo> observable = api.getWeatherInfo();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        info.setText(weatherInfo.toString());
                    }
                });
        /*call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if (response != null && response.body() != null) {
                    info.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {

            }
        });*/
    }

    @OnClick(R.id.download)
    public void onDownloadClick() {
        Observable<ResponseBody> observable = downloadApi.downloadPic("201608/18/20160818131233_uPyQ8.thumb.700_0.png");

        final File file = new File(Environment.getExternalStorageDirectory(), "mm.png");
        FileDownloadHelper.downLoadFile(observable, file, new FileDownloadHelper.DownloadListener() {
            @Override
            public void onDownloadStart() {
            }

            @Override
            public void onDownloading(int progress) {
            }

            @Override
            public void onDownloadComplete() {
                Toast.makeText(MainActivity.this, "complete", Toast.LENGTH_LONG).show();
                image.setImageURI(Uri.fromFile(file));
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
