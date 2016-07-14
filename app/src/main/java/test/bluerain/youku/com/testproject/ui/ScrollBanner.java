package test.bluerain.youku.com.testproject.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Project: TestProject.
 * Data: 2016/5/5.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public abstract class ScrollBanner<T> extends RelativeLayout {

    private Context mContext;

    private Scroller mScroller;
    private View mContentView;

    private Handler mHandler;

    private List<T> mData;

    private int mCurrnetPosition;

    public ScrollBanner(Context context) {
        this(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initVariable();
        initView();
    }

    private void initVariable() {
        mData = new ArrayList<>();
        mScroller = new Scroller(mContext);
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void initView() {
        if (!isHaveData())
            return;
        mContentView = getView(null, 0, mData.get(0));
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                new Timer(true).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startScroll();
                    }
                }, 0, 3000);
            }
        });
    }

    public void setData(List<T> data) {
        mData = data;
    }

    private boolean isHaveData() {
        if (null == mData)
            return false;
        if (mData.size() == 0)
            return false;
        return true;
    }

    protected abstract View getView(View convertView, int position, T data);

    private void startScroll() {
        this.post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, mContentView.getMeasuredHeight());
            }
        });
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
