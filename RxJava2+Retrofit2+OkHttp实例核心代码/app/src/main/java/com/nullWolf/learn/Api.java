package com.nullWolf.learn;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/9
 */
public interface Api {
    @GET(NetUrl.GET_DATA)
    Observable<GetDataResultBean> getData(@QueryMap Map<String, String> map);
}
