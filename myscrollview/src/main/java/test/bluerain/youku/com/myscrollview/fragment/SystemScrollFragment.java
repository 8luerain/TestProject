package test.bluerain.youku.com.myscrollview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import test.bluerain.youku.com.myscrollview.R;

/**
 * Project: TestProject.
 * Data: 2016/7/14.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class SystemScrollFragment extends Fragment {
    private static final String TAG = "SystemScrollFragment";

    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system_scroll_view, null);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        mTextView = (TextView) rootView.findViewById(R.id.txv_fragment_system_test);
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < 200; j++) {
            Random random = new Random();
            builder.append((char) random.nextInt(40) + 40);
        }
        mTextView.setText(builder.toString());
        rootView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "initViews: SystemtextviewSize [" + mTextView.getMeasuredHeight() + "]");
            }
        });
    }
}
