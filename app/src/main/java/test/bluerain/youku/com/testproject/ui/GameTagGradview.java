package test.bluerain.youku.com.testproject.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.List;

/**
 * Project: TestProject.
 * Data: 2016/5/9.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class GameTagGradview extends FrameLayout {
    private Context mContext;
    private GridView mRootView;
    private GameTagGradViewAdapter mGameTagGradViewAdapter;

    public void setGameTagGradViewAdapter(GameTagGradViewAdapter gameTagGradViewAdapter) {
        mGameTagGradViewAdapter = gameTagGradViewAdapter;
    }

    public <T> void setData(List<T> data) {
        if (null == data)
            return;
        mGameTagGradViewAdapter.setData(data);
        mRootView.setAdapter(mGameTagGradViewAdapter);
        mGameTagGradViewAdapter.notifyDataSetChanged();
    }


    public GameTagGradview(Context context) {
        this(context, null);
    }

    public GameTagGradview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameTagGradview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mRootView = new GridView(getContext());
        addView(mRootView, new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public static abstract class GameTagGradViewAdapter<T> extends BaseAdapter {

        private List<T> mData;

        public void setData(List<T> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public T getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return convertView(getItem(position), position);
        }

        protected abstract View convertView(T data, int position);
    }
}
