package com.bignerdranch.myowngank3.retrofit;


import com.bignerdranch.myowngank3.bean.GanHuo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by gaohailong on 2016/5/17.
 */
public interface GankService {
    @GET("api/data/{type}/{count}/{page}")
    Observable<GanHuo> getGanHuo(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
