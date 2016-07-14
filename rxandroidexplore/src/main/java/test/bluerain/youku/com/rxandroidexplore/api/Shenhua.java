package test.bluerain.youku.com.rxandroidexplore.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Project: TestProject.
 * Data: 2016/5/30.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class Shenhua {
    /*http://api.shjmpt.com:9002/pubApi/uLogin?uName=用户名&pWord=密码&Developer=开发者参*/

    public static final String BASE_URL = "http://api.shjmpt.com:9002/pubApi/";
    //滴滴
    public static final String USER = "mxh222";
    public static final String PASS = "shenhua2650122";
    public static final String DEVELOPER = "md%2fWxWnLcS23LuX%2fBXfvjQ%3d%3d";
    private static final Shenhua INSTANCE = new Shenhua();

    private Retrofit mRetrofitShenhua;

    private Shenhua() {
        mRetrofitShenhua = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Shenhua getInstance() {
        return INSTANCE;
    }

    interface GetTokenService {
        @GET("uLogin")
        Observable<String> getToken(@Query("uName") String userName, @Query("pWord") String password, @Query("Developer") String developer);
    }

    interface GetTokenServiceByCall {
        @GET("uLogin")
        Call<String> getToken(@Query("uName") String userName, @Query("pWord") String password, @Query("Developer") String developer);
    }
    public Observable<String> getToken() {
        GetTokenService getTokenService = mRetrofitShenhua.create(GetTokenService.class);
        return getTokenService.getToken(USER, PASS, DEVELOPER);
    }

    public Call<String> getTokenByCall() {
        GetTokenServiceByCall GetTokenServiceByCall = mRetrofitShenhua.create(GetTokenServiceByCall.class);
        return GetTokenServiceByCall.getToken(USER, PASS, DEVELOPER);
    }
}
