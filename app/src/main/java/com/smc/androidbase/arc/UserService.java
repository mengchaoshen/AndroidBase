package com.smc.androidbase.arc;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2017/7/25
 * @description
 */

public interface UserService {

    @POST("/confirm.asp")
    Observable<String> confirm(@Body LoginBean loginBean);


    @GET("/hrinfo/attendance/check.asp")
    Observable<String> check(@Query("action") String action,
                             @Query("r") String r);

    @GET("/login.asp")
    Observable<String> getLoginAsp();


}


