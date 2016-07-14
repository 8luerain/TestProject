package test.bluerain.youku.com.rxandroidexplore.api;

import android.util.Log;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import test.bluerain.youku.com.rxandroidexplore.utils.FileUtils;

/**
 * Project: TestProject.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class YoukuGameCenter {
    private static final String TAG = "YoukuGameCenter";
    public static final String BASE_URL = "http://222.73.61.144/youku/";

    private static YoukuGameCenter ourInstance = new YoukuGameCenter();

    private Retrofit mRetrofit;

    public static YoukuGameCenter getInstance() {
        return ourInstance;
    }

    private YoukuGameCenter() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    interface GetContentLength {
        /*http://222.73.61.144/youku/6975A9A2C20317EE588542E6A/030002070155FEC18FA54C2BEEFCF9E9AC7F8C-D2FF-E862-894A-57F74A9FD322.flv*/
        @GET("6975A9A2C20317EE588542E6A/030002070155FEC18FA54C2BEEFCF9E9AC7F8C-D2FF-E862-894A-57F74A9FD322.flv")
        Call<ResponseBody> getContentLength();

        @GET("6975A9A2C20317EE588542E6A/030002070155FEC18FA54C2BEEFCF9E9AC7F8C-D2FF-E862-894A-57F74A9FD322.flv")
        Call getContentLengthNew();

    }


    public void getContentLengthNew() {
        GetContentLength getContentLength = mRetrofit.create(GetContentLength.class);
        Call contentLengthNew = getContentLength.getContentLengthNew();
        contentLengthNew.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                String s = response.headers().get("Content-Length");
                Log.d(TAG, "onResponse: Content-Length[" + s + "]");
                Log.d(TAG, "onResponse: body [" + response.body() + "]");
                Log.d(TAG, "onResponse: " + response.headers() + "");
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
    public String getContentLength() {
        String length = null;
        GetContentLength getContentLength = mRetrofit.create(GetContentLength.class);
        Call<ResponseBody> contentLength = getContentLength.getContentLength();
        Call<ResponseBody> second = contentLength.clone();
        contentLength.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = response.headers().get("Content-Length");
                Log.d(TAG, "onResponse: Content-Length[" + s + "]");
                Log.d(TAG, "onResponse: body [" + response.body() + "]");
                Log.d(TAG, "onResponse: " + response.headers() + "");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        second.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream inputStream = response.body().byteStream();
                FileUtils.saveFile(inputStream, "test.flv");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
        return length;
    }
}
