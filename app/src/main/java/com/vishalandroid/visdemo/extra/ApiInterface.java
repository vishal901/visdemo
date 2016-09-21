package com.vishalandroid.visdemo.extra;


import com.vishalandroid.visdemo.response.GetData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lenovoi3 on 8/8/2016.
 */
public interface ApiInterface {




    @GET(AppConfig.URL_FINDUS)
    Call<GetData> findus();


}
