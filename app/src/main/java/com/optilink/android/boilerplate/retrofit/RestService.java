package com.optilink.android.boilerplate.retrofit;

import com.optilink.android.boilerplate.pojo.BaseResponse;
import com.optilink.android.boilerplate.pojo.Contributor;
import com.optilink.android.boilerplate.pojo.Driver;
import com.optilink.android.boilerplate.pojo.WorkOrderData;
import com.optilink.android.boilerplate.pojo.WorkOrderTodo;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by tg on 2015/10/17.
 */
public interface RestService {

    @GET("https://api.github.com/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @POST("/drivers/login")
    Observable<Driver> login(
            @Query("username") String username,
            @Query("password") String password);

    @GET("/work_orders/todo_list")
    Observable<WorkOrderTodo> todoList(
            @Query("uid") String uid,
            @Query("token") String token);

    @GET("/work_orders/show")
    Observable<WorkOrderData> showWorkOrder(
            @Query("uid") String uid,
            @Query("token") String token,
            @Query("work_order_id") long work_order_id);

    @POST("/work_orders/upload_task_attachment")
    Observable<BaseResponse> uploadTaskAttachment(
            @Query("uid") String uid,
            @Query("token") String token,
            @Query("task_id") long task_id,
            @Body RequestBody file);

    @GET
    Observable<Response<ResponseBody>> downloadAttachment(@Url String url);
}
