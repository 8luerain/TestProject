package test.bluerain.youku.com.testproject.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import test.bluerain.youku.com.testproject.R;

/**
 * Project: TestProject.
 * Data: 2016/5/9.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class GameFlowLayout extends ViewGroup {

    private Context mContext;

    //行间距
    private int mHorizontalSpacing;
    //列间距
    private int mVerticalSpacing;
    //列数
    private int mColumnNum;
    //列宽总和
    private int mVerticalSpacingSum;
    //Table视图下列宽
    private int mPerChildWidth;

    //记录每个View的左上角坐标
    private List<Point> mChildPos = new ArrayList();

    public GameFlowLayout(Context context) {
        this(context, null);
    }

    public GameFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initTypedValues(attrs, defStyle);
        mVerticalSpacingSum = mVerticalSpacing * (mColumnNum - 1);
    }

    private void initTypedValues(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.GameFlowLayout, defStyle, 0);
        mColumnNum = typedArray.getInt(R.styleable.GameFlowLayout_columnNum, 0);
        if (mColumnNum == 0)
            return;
        mHorizontalSpacing = (int) typedArray.getDimension(R.styleable.GameFlowLayout_horizontalSpacing, 0);
        mVerticalSpacing = (int) typedArray.getDimension(R.styleable.GameFlowLayout_verticalSpacing, 0);
        Log.d("TAG", "initTypedValues: mHorizontalSpacing [" + mHorizontalSpacing + "],mVerticalSpacing[" + mVerticalSpacing + "],mColumnNum[" +
                mColumnNum + "]");
        typedArray.recycle();
    }


    private boolean isTableLayout() {
        return mColumnNum > 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mChildPos.clear();
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        //子View数量
        int childCount = getChildCount();
        //控件最终的宽高
        int width = 0, height = 0;
        //每一行的宽高
        int lineWidth = 0, lineHeight = 0;
        int otherParentWidth = parentWidth - getPaddingLeft() - getPaddingRight();// 去除父类内边距,用于计算指定列数后，每个孩子应该找有的宽度
        mPerChildWidth = (int) ((otherParentWidth * 1f - mVerticalSpacingSum) / mColumnNum);
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
            if (isTableLayout())
                marginLayoutParams.width = mPerChildWidth;
            int childViewWidth = childView.getVisibility() == GONE ?
                    0 : childView.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            Log.d("TAG", "onMeasure: childViewWidth:["+childViewWidth+"] mPerChildWidth["+mPerChildWidth+"]");
            int childViewHeight = childView.getVisibility() == GONE ?
                    0 : childView.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if (lineWidth + childViewWidth > parentWidth - getPaddingLeft() - getPaddingRight() || isFirstColumn(i)) {//换行
                width = Math.max(width, lineWidth);
                height += lineHeight;
                lineWidth = (isTableLayout() ? mPerChildWidth : childViewWidth);
                lineHeight = childViewHeight;
                mChildPos.add(new Point(
                        getPaddingLeft() + marginLayoutParams.leftMargin + ((getCurrentColumnNum(i + 1) - 1) * mVerticalSpacing),
                        height + getPaddingTop() + marginLayoutParams.topMargin + ((getCurrentLineNum(i + 1) - 1) * mHorizontalSpacing)
                ));

            } else {
                //更新行宽
                mChildPos.add(new Point(
                        lineWidth + getPaddingLeft() + marginLayoutParams.leftMargin + ((getCurrentColumnNum(i + 1) - 1) * mVerticalSpacing),
                        height + getPaddingTop() + marginLayoutParams.topMargin + ((getCurrentLineNum(i + 1) - 1) * mHorizontalSpacing)
                ));
                lineWidth += (isTableLayout() ? mPerChildWidth : childViewWidth);
                lineHeight = Math.max(childViewHeight, lineHeight);
            }

            if (i == getChildCount() - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        Log.d("TAG", "onMeasure: parentWidth " + parentWidth + ": parentHeight: " + parentHeight + " width :" + width + " height:" + height + "");

        setMeasuredDimension(
                parentWidthMode == MeasureSpec.EXACTLY ? parentWidth :
                        width + getPaddingLeft() + getPaddingRight()
                                + (mVerticalSpacing * (mColumnNum - 1)),
                parentHeightMode == MeasureSpec.EXACTLY ? parentHeight :
                        height + getPaddingTop() + getPaddingBottom()
                                + (mHorizontalSpacing * (getCurrentLineNum(childCount) - 1)));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Point viewPosition = mChildPos.get(i);
            childAt.layout(
                    viewPosition.x,
                    viewPosition.y,
                    viewPosition.x + (isTableLayout() ? mPerChildWidth : childAt.getMeasuredWidth()),
                    viewPosition.y + childAt.getMeasuredHeight()
            );
        }
    }

    private int getCurrentLineNum(int i) {
        if (mColumnNum == 0)
            return 0;
        return (i % mColumnNum == 0) ?
                i / mColumnNum :
                (i / mColumnNum + 1);
    }

    private int getCurrentColumnNum(int i) {
        if (mColumnNum == 0)
            return 0;
        return (i % mColumnNum == 0) ?
                mColumnNum :
                i % mColumnNum;

    }

    private boolean isFirstLine(int i) {
        if (mColumnNum == 0)
            return false;
        return i / mColumnNum == 0;

    }

    private boolean isFirstColumn(int i) {
        if (mColumnNum == 0)
            return false;
        return i % mColumnNum == 0;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT);
    }
}
