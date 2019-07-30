package com.bignerdranch.myowngank3.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankRetrofit
{
    private static final String GANHUO_API="http://gank.io/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            synchronized (GankRetrofit.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(GANHUO_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
