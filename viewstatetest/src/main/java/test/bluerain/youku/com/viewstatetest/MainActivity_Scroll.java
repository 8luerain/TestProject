package test.bluerain.youku.com.viewstatetest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Project: TestProject.
 * Data: 2016/5/23.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class MainActivity_Scroll extends Activity {
    private LinearLayout mLinearLayout;
    private ArrayList<Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main_scroll);
        initData();
        initView();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add(i + "Itme ...");
        }
    }

    private void initView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.id_layout_line_container);
        initContainerView();
    }

    private void initContainerView() {
        for (int i = 0; i < mData.size(); i++) {
            String s = (String) mData.get(i);
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_listview_main, null);
            TextView textView = (TextView) itemView.findViewById(R.id.id_txv_item_listview_main);
            textView.setText(s);
//            itemView.setId(View.generateViewId());
            mLinearLayout.setSaveEnabled(true);
            mLinearLayout.addView(itemView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                    .WRAP_CONTENT));

        }
    }





}
