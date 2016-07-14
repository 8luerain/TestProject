package test.bluerain.youku.com.androidtestsection3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;

import test.bluerain.youku.com.androidtestsection3.view.MoveWithFingerView;

public class MainActivity extends AppCompatActivity {

    private Button mBtnMainStartMove;
    private MoveWithFingerView mViewTargetMain;

    private Scroller mScroller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVaryablies();
        initViews();
        initListeners();

    }

    private void initVaryablies() {
        mScroller = new Scroller(this);
    }

    private void initViews() {
        mBtnMainStartMove = (Button) findViewById(R.id.btn_main_start_move);
        mViewTargetMain = (MoveWithFingerView) findViewById(R.id.view_target_main);

    }

    private void initListeners() {
        mBtnMainStartMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ObjectAnimator.ofFloat(mViewTargetMain, "translationX", 0, 100).setDuration(1000).start();
                mViewTargetMain.startSmoothScroll(100, 0);
            }

        });
    }


}
