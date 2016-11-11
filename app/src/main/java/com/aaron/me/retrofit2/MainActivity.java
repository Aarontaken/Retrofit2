package com.aaron.me.retrofit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import module.WeatherInfo;
import request.MainApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit2;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.request)
    Button request;
    @InjectView(R.id.info)
    TextView info;
    private MainApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        api = Retrofit2.INSTANCE.getRetrofit().create(MainApi.class);
    }

    @OnClick(R.id.request)
    public void onClick() {
        Call<WeatherInfo> call = api.getWeatherInfo();
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if (response != null && response.body() != null) {
                    info.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {

            }
        });
    }
}
