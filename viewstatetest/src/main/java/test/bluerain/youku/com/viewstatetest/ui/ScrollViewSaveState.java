package test.bluerain.youku.com.viewstatetest.ui;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * Project: TestProject.
 * Data: 2016/5/23.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class ScrollViewSaveState extends ScrollView {
    public ScrollViewSaveState(Context context) {
        this(context, null);
    }

    public ScrollViewSaveState(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewSaveState(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SaveState ss = new SaveState(super.onSaveInstanceState());
        ss.mScrollY = getScrollY();
        Log.d("TAG", "onSaveInstanceState: mScrollY["+ss.mScrollY+"]");
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SaveState ss = (SaveState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setScrollY(ss.mScrollY);
        Log.d("TAG", "onRestoreInstanceState: mScrollY["+ss.mScrollY+"]");
    }


    static class SaveState extends View.BaseSavedState {
        int mScrollY;
        public SaveState(Parcel source) {
            super(source);
            mScrollY = source.readInt();
        }

        public SaveState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mScrollY);
        }

        public static final Parcelable.Creator<SaveState> CREATOR = new Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel source) {
                return new SaveState(source);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
    }


}
