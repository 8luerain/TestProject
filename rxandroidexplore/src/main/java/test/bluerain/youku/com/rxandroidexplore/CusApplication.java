package test.bluerain.youku.com.rxandroidexplore;

import android.app.Application;
import android.content.Context;

/**
 * Project: TestProject.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class CusApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
