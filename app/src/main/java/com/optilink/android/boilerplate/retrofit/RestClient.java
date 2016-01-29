package com.optilink.android.boilerplate.retrofit;

import android.content.Context;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscription;
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

    Context context;
    String uid = null;
    String token = null;
    OkHttpClient client;
    Retrofit retrofit;
    RestService service;
    ActionCreatorImpl actionCreator;
    List<Subscription> subscriptions;

    RestClient(Context context) {
        this.context = context;
        subscriptions = new ArrayList<>();
        actionCreator = ActionCreatorImpl.getInstance(Dispatcher.getInstance());

        client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        Timber.d("[intercept] %s", request);
                        String contentType = response.headers().get("Content-Type");
                        if (!TextUtils.isEmpty(contentType) && contentType.startsWith("application/json;")) {
                            // Response bodies can only be read once
                            String bodyString = response.body().string();
                            Timber.d("[intercept] Response:%s", bodyString);
                            // TODO add your code

                            return response.newBuilder()
                                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                                    .build();
                        }

                        /**
                         * 文件下载时，ResponseBody.create(response.body().contentType(), bodyString)
                         *  构造的 body 的 contentLength 和实际 contentLength 不一致，导致文件下载错误
                         */
                        return response;
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

    public static RestClient getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RestClient(context.getApplicationContext());
        }
        return sInstance;
    }

    class Subscriber<T> extends rx.Subscriber<T> {
        String action;

        public Subscriber(String action) {
            super();
            this.action = action;
            add(this);
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
            Timber.e(e, e.toString());
            actionCreator.sendAction(ActionConf.ACTION_DISMISS_PROGRESS_DIALOG);
            actionCreator.sendAction(ActionConf.ACTION_REPORT_ERROR, ActionConf.ACTION_KEY_ONLY_ONE, e);
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

    public void unsubscribe() {
        for (Subscription obj : subscriptions) {
            if (!obj.isUnsubscribed()) {
                obj.unsubscribe();
            }
        }
        subscriptions.clear();
    }

    public void login(String username, String password) {
        sendActionProgressDialog();

        Subscription subscription = getService()
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Driver>(ActionConf.ACTION_LOGIN) {
                    @Override
                    public void onNext(final Driver driver) {
                        setAuthenticate(driver);
                        super.onNext(driver);
                    }
                });

        subscriptions.add(subscription);
    }

    public void todoList() {
        sendActionProgressDialog();

        if (isAuthenticated()) {
            Subscription subscription = getService()
                    .todoList(uid, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkOrderTodo>(ActionConf.ACTION_WORK_ORDER_TODO_LIST));

            subscriptions.add(subscription);
        } else {
            Subscription subscription = getService()
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

            subscriptions.add(subscription);
        }
    }

    public void showWorkOrder(final long workOrderId) {
        sendActionProgressDialog();

        if (isAuthenticated()) {
            Subscription subscription = getService()
                    .showWorkOrder(uid, token, workOrderId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkOrderData>(ActionConf.ACTION_SHOW_WORK_ORDER));

            subscriptions.add(subscription);
        } else {
            Subscription subscription = getService()
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

            subscriptions.add(subscription);
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
            Subscription subscription = getService()
                    .uploadTaskAttachment(uid, token, taskId, fileRequestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseResponse>(ActionConf.ACTION_UPLOAD_TASK_ATTACHMENT));

            subscriptions.add(subscription);
        } else {
            Subscription subscription = getService()
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

            subscriptions.add(subscription);
        }
    }

    public void downloadAttachment(final String url) {
        Subscription subscription = getService()
                .downloadAttachment(url)
                .map(new Func1<retrofit2.Response<ResponseBody>, File>() {
                    @Override
                    public File call(retrofit2.Response<ResponseBody> response) {
                        return saveFile(url, response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>(ActionConf.ACTION_DOWNLOAD_ATTACHMENT));

        subscriptions.add(subscription);
    }

    public void contributors(String owner, String repo) {
        Subscription subscription = getService()
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

        subscriptions.add(subscription);
    }

    private void print(Response response) {
        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            Timber.d("%s: %s", responseHeaders.name(i), responseHeaders.value(i));
        }

        try {
            Timber.d(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File saveFile(String url, final retrofit2.Response<ResponseBody> response) {
        try {
            int end = url.lastIndexOf('?');
            if (end < 0) {
                end = url.length();
            }
            String fileName = url.substring(url.lastIndexOf('/') + 1, end);
            File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName);

            ResponseBody body = response.body();
            long contentLength = body.contentLength();

            BufferedSource source = body.source();
            BufferedSink sink = Okio.buffer(Okio.sink(file));
//            long count = 0;
//            long total = 0;
//            while ((count = source.read(sink.buffer(), 1024)) != -1) {
//                total += count;
//                Timber.d("%d", total * 100 / contentLength);
//            }
            sink.writeAll(source);
            sink.close();

            Timber.d("Head Content-Length:%s, BodyContentLength:%d, FileLength:%d",
                    response.headers().get("Content-Length"), contentLength, file.length());

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}
