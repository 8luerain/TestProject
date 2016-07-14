package test.bluerain.youku.com.myscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Project: TestProject.
 * Data: 2016/7/13.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class MyScroll extends ViewGroup {

    private static final String TAG = "MyScroll";

    private float mLastX;
    private float mLastY;

    private int mMaxTopMargin;//最大上边距，用于越界检查
    private int mMinTopMargin;

    private int mOriginalTopMargin;
    private int mOriginalBottomMargin;
    private boolean isStoreValue; //Margin初始值是否记录

    private VelocityTracker mVelocityTracker;

    private Scroller mScroller;

    private View mContent;
    private MarginLayoutParams mContentMarginLP;

    public MyScroll(Context context) {
        this(context, null);
    }

    public MyScroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVarables();
    }

    private void initVarables() {
        mVelocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth = 0;
        int measuredHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        Log.d(TAG, "onMeasure: mesured [" + getChildAt(0).getMeasuredHeight() + "]");
        if (haveNoChildren()) {
            /*当没有孩子时，wrap_content为0 ， 其他的为原始设定值*/
            setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? 0 : widthSize, heightMode == MeasureSpec.AT_MOST ? 0 : heightSize);
            return;
        }

        // 只考虑ScrollView 有一个孩子的情况
        mContent = getChildAt(0);
        mContentMarginLP = (MarginLayoutParams) mContent.getLayoutParams();

        int childWidthSpace = mContent.getMeasuredWidth() + mContentMarginLP.leftMargin + mContentMarginLP.rightMargin +
                getPaddingLeft() + getPaddingRight();

        int childHeightSpace = mContent.getMeasuredHeight() + mContentMarginLP.topMargin + mContentMarginLP.bottomMargin +
                getPaddingTop() + getPaddingBottom();

        measuredWidth = widthMode == MeasureSpec.AT_MOST ? Math.min(childWidthSpace, widthSize) : widthSize;
        measuredHeight = heightMode == MeasureSpec.AT_MOST ? Math.min(childHeightSpace, heightSize) : heightSize;

        setMeasuredDimension(measuredWidth, measuredHeight);
        Log.d(TAG, "onMeasure: mContent.getMeasuredHeight() [" + mContent.getMeasuredHeight() + "]");
        if (!isStoreValue) {
            mOriginalTopMargin = mContentMarginLP.topMargin;
            mOriginalBottomMargin = mContentMarginLP.bottomMargin;
            Log.d(TAG, "onMeasure: mOriginalTopMargin [" + mOriginalTopMargin + "] mOriginalBottomMargin[" + mOriginalBottomMargin + "]");
            mMaxTopMargin = mOriginalTopMargin;
            mMinTopMargin = -(mOriginalTopMargin + mOriginalBottomMargin + mContent.getMeasuredHeight() - getMeasuredHeight());
            Log.d(TAG, "onLayout: mMaxTopMargin [" + mMaxTopMargin + "] mMinTopMargin[" + mMinTopMargin + "] " +
                    "mContent.getMeasuredHeight()[" + mContent.getMeasuredHeight() + "]  getMeasuredHeight() [" + getMeasuredHeight() + "]");
            isStoreValue = true;
        }

    }


    private boolean haveNoChildren() {
        return getChildCount() == 0;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (haveNoChildren())
            return;
        int l_firstChild = getPaddingLeft() + mContentMarginLP.leftMargin;
        int t_firstChild = getPaddingTop() + mContentMarginLP.topMargin;
        mContent.layout(l_firstChild, t_firstChild, l_firstChild + mContent.getMeasuredWidth(), t_firstChild + mContent.getMeasuredHeight());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        float currentX = event.getX();
        float currentY = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                mLastX = currentX;
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = currentX - mLastX;
                float dy = currentY - mLastY;
                scrollToNewPosition(dx, dy);
                mLastX = currentX;
                mLastY = currentY;
                Log.d(TAG, "onTouchEvent: ACTION_MOVE : dx [" + dx + "] dy[" + dy + "]");
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float yVelocity = mVelocityTracker.getYVelocity();
//                mScroller.startScroll((int)mContent.getX(),(int)mContent.getY(),0,-100,1000);
//                mContent.postInvalidate();
                Log.d(TAG, "onTouchEvent: mContent.getY() [" + mContent.getY() + "]  mContent.getTranslationY() [" + mContent.getTranslationY() + "] ");
                Log.d(TAG, "onTouchEvent: ACTION_UP &  mContent.getScrollY() [" + mContentMarginLP.topMargin + "] & yVelocity [" + yVelocity + "]");
                break;
        }
        return true;
    }

    private void scrollToNewPosition(float dx, float dy) {
        int newTopMargin = (int) (mContentMarginLP.topMargin + dy);

        if (newTopMargin > mMaxTopMargin)
            newTopMargin = mMaxTopMargin;
//        if (newTopMargin < mMinTopMargin)
//            newTopMargin = mMinTopMargin;

        mContentMarginLP.topMargin = newTopMargin;
        requestLayout();
//        float newTransY = mContent.getTranslationY() + dy;
//        mContent.setTranslationY(newTransY);
//        mContent.setY(mContent.getY() + dy);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

//    @Override
//    public void computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            mContent.postInvalidate();
//        }
//    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
