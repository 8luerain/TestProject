package test.bluerain.youku.com.androidtestsection3.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Project: TestProject.
 * Data: 2016/7/11.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class MoveWithFingerView extends View {
    private static final String TAG = "MoveWithFingerView";

    private Scroller mScroller;

    public MoveWithFingerView(Context context) {
        this(context, null);
    }

    public MoveWithFingerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveWithFingerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
    }


    public void startSmoothScroll(int desX, int desY) {
        Log.d(TAG, "startSmoothScroll: getScrollX() = [" + getScrollX() + "] getScrollY() [" + getScrollY() + "]");
        mScroller.startScroll(getScrollX(), getScrollY(), getScrollX() - desX, getScrollY() - desY, 1000);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);

    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            Log.d(TAG, "computeScroll: getCurrX() =["+mScroller.getCurrX()+"]  mScroller.getCurrY() ["+mScroller.getCurrY()+"] ");
            postInvalidate();
        }
    }
}
