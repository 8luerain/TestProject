package test.bluerain.youku.com.rxandroidexplore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.bluerain.youku.com.rxandroidexplore.api.Shenhua;
import test.bluerain.youku.com.rxandroidexplore.api.YoukuGameCenter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mIdTxvMainResult;
    private Button mIdBtnMainSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mIdTxvMainResult = (TextView) findViewById(R.id.id_txv_main_result);
        mIdBtnMainSend = (Button) findViewById(R.id.id_btn_main_send);
        mIdBtnMainSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getVideoData();
//                getVideoDataByCall();
//                YoukuGameCenter.getInstance().getContentLength();
                YoukuGameCenter.getInstance().getContentLengthNew();
            }
        });
    }

    private void getVideoData() {
        Observable<String> token = Shenhua.getInstance().getToken();
        token.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart: ..");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ..");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                    }
                });
    }

    private void getVideoDataByCall() {
        Call<String> tokenByCall = Shenhua.getInstance().getTokenByCall();
        tokenByCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.headers().get("Content-Length");
                Log.d(TAG, "onResponse: Content-Length[" + s + "]");
                Log.d(TAG, "onResponse: body [" + response.body() + "]");
                Log.d(TAG, "onResponse: " + response.headers() + "");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

    }


}
