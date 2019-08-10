package com.meiliwu.installer.api;

import com.meiliwu.installer.entity.APKEntity;
import com.meiliwu.installer.entity.PackageEntity;
import com.meiliwu.installer.entity.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author:  ZhangTao
 * Date: 2018/3/5.
 */

public interface ApiService {
    /*获取所有APP列表*/
    @Headers("Cache-Control:0")
    @GET("/applicationClient/getApplications")
    Observable<Result<List<PackageEntity>>> getApplicationList();

    /*获取指定平台的特定APK(release/debug)列表*/
    @Headers("Cache-Control:0")
    @GET("/applicationClient/getApplicationVersions")
    Observable<Result<List<APKEntity>>> getPackageList(@Query("system_name") String system_name, @Query("application_id") String application_id, @Query("version_type") String version_type, @Query("page") int pageIndex);

}
