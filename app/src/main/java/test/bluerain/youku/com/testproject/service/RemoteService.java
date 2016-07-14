package test.bluerain.youku.com.testproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Project: TestProject.
 * Data: 2016/4/29.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class RemoteService extends Service {

    private int mCount;

    protected class ServiceSub extends Binder {

        public synchronized void addCount() {
            mCount++;
        }


    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
