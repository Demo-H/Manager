package com.dhunter.common.network;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by dhunter on 2018/6/25.
 */

public interface BaseApiService {
    @POST()
    @FormUrlEncoded
    Flowable<ResponseBody> executePost(
            @Url() String url,
            @FieldMap Map<String, Object> maps);

    @POST("{url}")
    Flowable<ResponseBody> executePostBody(
            @Path("url") String url,
            @Body Object object);

    @GET()
    Observable<ResponseBody> executeGet(
            @Url String url,
            @QueryMap Map<String, Object> maps);



    @Multipart
    @POST()
    Flowable<ResponseBody> upLoadImage(
            @Url() String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Multipart
    @POST()
    Flowable<ResponseBody> uploadFlie(
            @Url String fileUrl,
            @Part("description") RequestBody description,
            @Part("files") MultipartBody.Part file);


    @POST()
    Flowable<ResponseBody> uploadFiles(
            @Url() String url,
            @Body Map<String, RequestBody> maps);

    @POST()
    Flowable<ResponseBody> uploadFile(
            @Url() String url,
            @Body RequestBody file);

    @Multipart
    @POST
    Flowable<ResponseBody> uploadFileWithPartMap(
            @Url() String url,
            @PartMap() Map<String, RequestBody> partMap,
            @Part("file") MultipartBody.Part file);

    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String fileUrl);


    @GET
    Flowable<ResponseBody> downloadSmallFile(@Url String fileUrl);


    @GET
    Flowable<ResponseBody> getTest(@Url String fileUrl,
                                   @QueryMap Map<String, Object> maps);

    @FormUrlEncoded
    @POST()
    Flowable<ResponseBody> postForm(
            @Url() String url,
            @FieldMap Map<String, Object> maps);


    @POST()
    Flowable<ResponseBody> postRequestBody(
            @Url() String url,
            @Body RequestBody Body);

}