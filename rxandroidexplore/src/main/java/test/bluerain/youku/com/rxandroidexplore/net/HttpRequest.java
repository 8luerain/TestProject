package test.bluerain.youku.com.rxandroidexplore.net;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.bluerain.youku.com.rxandroidexplore.entities.ResponseEntity;

/**
 * Project: TestProject.
 * Data: 2016/5/30.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class HttpRequest {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    interface GetVideoService {
        /*https://api.douban.com/v2/movie/top250?start=0&count=10*/
        @GET("top250")
        Call<ResponseEntity> getVoideoData(@Query("start") int start, @Query("count") int count);
    }

    public static Call<ResponseEntity> getVideoData() {
        Retrofit.Builder builder = new Retrofit.Builder();
        GetVideoService videoService = builder
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GetVideoService.class);
        return videoService.getVoideoData(0, 10);
    }
}
