package com.vishalandroid.visdemo.extra;


import com.vishalandroid.visdemo.response.GetData;
import com.vishalandroid.visdemo.response.abc;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vishal on 26/9/16.
 */
public interface ApiInterface {




    @GET(AppConfig.URL_FINDUS)
    Call<GetData> findus();


    @GET(AppConfig.URL_FINDUS)
    Call<abc> getdata();
}
