package test.bluerain.youku.com.testproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rain on 2016/8/25.
 */
public class CommonUtils {
    public static CommonUtils INSTANCE;

    private static Context mContext;

    private CommonUtils(Context mContext) {
        this.mContext = mContext;
    }


    public static CommonUtils getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CommonUtils(context);
        }
        if (mContext == null){
            mContext = context;
        }
        return INSTANCE;
    }

    public SharedPreferences getSharedPF() {
        return mContext.getSharedPreferences("jskd", 10);

    }

    public void release(){
        mContext = null;
    }


}
