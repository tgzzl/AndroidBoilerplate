package com.optilink.android.boilerplate.retrofit;

import android.os.Environment;
import android.text.TextUtils;

import com.optilink.android.boilerplate.ActionConf;
import com.optilink.android.boilerplate.flux.Dispatcher;
import com.optilink.android.boilerplate.flux.action.ActionCreatorImpl;
import com.optilink.android.boilerplate.pojo.BaseResponse;
import com.optilink.android.boilerplate.pojo.Contributor;
import com.optilink.android.boilerplate.pojo.Driver;
import com.optilink.android.boilerplate.pojo.WorkOrderData;
import com.optilink.android.boilerplate.pojo.WorkOrderTodo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by tanner.tan on 2016/1/27.
 */
public class RestClient {
    static final String ENDPOINT = "http://dev-ldms-rws.optilink.com:8192";
    static RestClient sInstance;

    String uid = null;
    String token = null;
    Retrofit retrofit;
    RestService service;
    ActionCreatorImpl actionCreator;

    RestClient() {
        actionCreator = ActionCreatorImpl.getInstance(Dispatcher.getInstance());

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        // Response bodies can only be read once
                        String bodyString = response.body().string();
                        String contentType = response.headers().get("Content-Type");
                        Timber.d("[intercept] %s", request);
                        if (!TextUtils.isEmpty(contentType) && contentType.startsWith("application/json;")) {
                            Timber.d("[intercept] response:%s", bodyString);
                        }

                        return response.newBuilder()
                                .body(ResponseBody.create(response.body().contentType(), bodyString))
                                .build();
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(RestService.class);
    }

    public static RestClient getInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    class Subscriber<T> extends rx.Subscriber<T> {
        String action;

        public Subscriber(String action) {
            this.action = action;
        }

        @Override
        public void onNext(T t) {
            actionCreator.sendAction(this.action, ActionConf.ACTION_KEY_ONLY_ONE, t);
        }

        @Override
        public void onCompleted() {
            actionCreator.sendAction(ActionConf.ACTION_DISMISS_PROGRESS_DIALOG);
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, e.getMessage());
            actionCreator.sendAction(ActionConf.ACTION_DISMISS_PROGRESS_DIALOG);
            actionCreator.sendAction(ActionConf.ACTION_KEY_ONLY_ONE, e);
        }
    }

    RestService getService() {
        return service;
    }

    void sendActionProgressDialog() {
        actionCreator.sendAction(ActionConf.ACTION_SHOW_PROGRESS_DIALOG);
    }

    void setAuthenticate(Driver driver) {
        uid = driver.uid;
        token = driver.token;
    }

    boolean isAuthenticated() {
        return !(TextUtils.isEmpty(uid) || TextUtils.isEmpty(token));
    }

    public void login(String username, String password) {
        sendActionProgressDialog();

        getService()
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Driver>(ActionConf.ACTION_LOGIN) {
                    @Override
                    public void onNext(Driver driver) {
                        super.onNext(driver);
                        setAuthenticate(driver);
                    }
                });
    }

    public void todoList() {
        sendActionProgressDialog();

        if (isAuthenticated()) {
            getService()
                    .todoList(uid, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkOrderTodo>(ActionConf.ACTION_WORK_ORDER_TODO_LIST));
        } else {
            getService()
                    .login("15818645501", "111111")
                    .flatMap(new Func1<Driver, Observable<WorkOrderTodo>>() {
                        @Override
                        public Observable<WorkOrderTodo> call(Driver driver) {
                            setAuthenticate(driver);
                            return getService().todoList(uid, token);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkOrderTodo>(ActionConf.ACTION_WORK_ORDER_TODO_LIST));
        }
    }

    public void showWorkOrder(final long workOrderId) {
        sendActionProgressDialog();

        if (isAuthenticated()) {
            getService()
                    .showWorkOrder(uid, token, workOrderId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkOrderData>(ActionConf.ACTION_SHOW_WORK_ORDER));
        } else {
            getService()
                    .login("15818645501", "111111")
                    .flatMap(new Func1<Driver, Observable<WorkOrderData>>() {
                        @Override
                        public Observable<WorkOrderData> call(Driver driver) {
                            setAuthenticate(driver);
                            return getService().showWorkOrder(uid, token, workOrderId);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkOrderData>(ActionConf.ACTION_SHOW_WORK_ORDER));
        }
    }

    public void uploadTaskAttachment(final long taskId, final String path) {
        sendActionProgressDialog();

        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        final RequestBody fileRequestBody = new MultipartBody.Builder()
                .addFormDataPart("attachment_file", file.getName(), fileBody)
                .build();

        if (isAuthenticated()) {
            getService()
                    .uploadTaskAttachment(uid, token, taskId, fileRequestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseResponse>(ActionConf.ACTION_UPLOAD_TASK_ATTACHMENT));
        } else {
            getService()
                    .login("15818645501", "111111")
                    .flatMap(new Func1<Driver, Observable<BaseResponse>>() {
                        @Override
                        public Observable<BaseResponse> call(Driver driver) {
                            setAuthenticate(driver);
                            return getService().uploadTaskAttachment(uid, token, taskId, fileRequestBody);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseResponse>(ActionConf.ACTION_UPLOAD_TASK_ATTACHMENT));
        }
    }

    public void downloadAttachment(String url) {
        getService()
                .downloadAttachment(url)
                .flatMap(new Func1<retrofit2.Response<ResponseBody>, Observable<File>>() {
                    @Override
                    public Observable<File> call(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        return saveFile(responseBodyResponse);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>(ActionConf.ACTION_DOWNLOAD_ATTACHMENT));
    }

    public void contributors(String owner, String repo) {
        getService()
                .contributors(owner, repo)
                .flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                    @Override
                    public Observable<Contributor> call(List<Contributor> contributors) {
                        return Observable.from(contributors);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Contributor>(ActionConf.ACTION_CONTRIBUTORS));
    }

    private Observable<File> saveFile(final retrofit2.Response<ResponseBody> response) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(rx.Subscriber<? super File> subscriber) {
                try {
                    // you can access headers of response
                    String header = response.headers().get("Content-Disposition");
                    // this is specific case, it's up to you how you want to save your file
                    // if you are not downloading file from direct link, you might be lucky to obtain file name from header
                    String fileName = header.replace("attachment; filename=", "").replaceAll("\"", "");
                    // will create file in global Music directory, can be any other directory, just don't forget to handle permissions
                    File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName);

                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    // you can access body of response
                    sink.writeAll(response.body().source());
                    sink.flush();
                    sink.close();

                    subscriber.onNext(file);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
