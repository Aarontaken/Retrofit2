package com.aaron.me.retrofit2;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import module.WeatherInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import request.ApiService;
import request.fileRequest.FileDownloadHelper;
import request.fileRequest.FileUploadHelper;
import retrofit2.Retrofit2;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
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
    @InjectView(R.id.uploadSingle)
    Button uploadSingle;
    @InjectView(R.id.uploadMulti)
    Button uploadMulti;
    private ApiService api;
    private ApiService downloadApi;
    private ApiService uploadApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        api = Retrofit2.INSTANCE.getRetrofit().create(ApiService.class);
        downloadApi = Retrofit2.DOWNLOAD_INSTANCE.getRetrofit().create(ApiService.class);
        uploadApi = Retrofit2.UPLOAD_INSTANCE.getRetrofit().create(ApiService.class);
    }

    @OnClick(R.id.request)
    public void onWeatherClick() {
        Observable<WeatherInfo> observable = api.getWeatherInfo();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "complete", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "error:"+e.toString(), Toast.LENGTH_LONG).show();
                        info.setText(e.toString());
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
                Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
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
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "error:"+e.toString(), Toast.LENGTH_LONG).show();
                image.setImageResource(R.mipmap.ic_launcher);
            }
        });
    }

    @OnClick({R.id.uploadSingle, R.id.uploadMulti})
    public void onClick(View view) {
        File file1 = new File(Environment.getExternalStorageDirectory(), "test.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test.jpg");
        Observable<ResponseBody> observable = null;
        switch (view.getId()) {
            case R.id.uploadSingle:
                RequestBody desc = FileUploadHelper.getRequestBodyString("file0"+"\"; filename=\""+file1.getName());
                MultipartBody.Part pic = FileUploadHelper.getMuMultipartBodyPart(file1);
                observable = uploadApi.uploadPic("user.updateAvatar", desc, pic);
                break;
            case R.id.uploadMulti:
                RequestBody pic1 = FileUploadHelper.getRequestBodyFile(file1);
                RequestBody pic2 = FileUploadHelper.getRequestBodyFile(file2);
                Map<String, RequestBody> fileMap = new HashMap<>();
                fileMap.put("file0"+"\"; filename=\""+file1.getName(), pic1);
                fileMap.put("file1"+"\"; filename=\""+file2.getName(), pic2);
                RequestBody descMulti = FileUploadHelper.getRequestBodyString("this is description");
                observable = uploadApi.uploadMultiPic("user.updateAvatar", descMulti, fileMap);
                break;
        }
        if (observable != null) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.e("call","上传完成");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("call",e.getMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {

                        }
                    });
        }
    }
}
