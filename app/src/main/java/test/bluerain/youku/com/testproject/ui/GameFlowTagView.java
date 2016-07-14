package test.bluerain.youku.com.testproject.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import test.bluerain.youku.com.testproject.R;

/**
 * Project: TestProject.
 * Data: 2016/5/9.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class GameFlowTagView extends FrameLayout {

    private Context mContext;
    private GameFlowLayout mRootView;

    private LayoutInflater mLayoutInflater;
    private GameFlowTagViewAdapter mGameFlowTagViewAdapter;

    private List mData;

    private OnItemClickListener mOnItemClickListener;

    public GameFlowTagView(Context context) {
        this(context, null);
    }

    public GameFlowTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameFlowTagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initVariables();
        mLayoutInflater.inflate(R.layout.game_layout_flow_tag, this);
        mRootView = (GameFlowLayout) findViewById(R.id.id_layout_game_flow);
        mRootView.post(new Runnable() {
            @Override
            public void run() {

//                Log.d("TAG", "run: paddingLeft["+getPaddingLeft()+"]: paddingRight["+getPaddingRight()+"]");
            }
        });

    }

    private void initVariables() {
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    private void initItemView(List data) {
        if (null == mGameFlowTagViewAdapter) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            final int position = i;
            final View itemView = mGameFlowTagViewAdapter.convert(mLayoutInflater, data.get(i), i);
            if (null == itemView)
                continue;
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnItemClickListener)
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                mOnItemClickListener.onItemClick(itemView, position);
                            }
                        });
                }
            });
            mRootView.addView(itemView);
        }
    }


    public void setGameFlowTagViewAdapter(GameFlowTagViewAdapter gameFlowTagViewAdapter) {
        mGameFlowTagViewAdapter = gameFlowTagViewAdapter;
    }

    public void setData(List data) {
        if (null == data || data.size() == 0)
            return;
        mData = data;
        initItemView(mData);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public abstract static class GameFlowTagViewAdapter<T> {

        public abstract View convert(LayoutInflater inflater, T data, int position);

    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

}
