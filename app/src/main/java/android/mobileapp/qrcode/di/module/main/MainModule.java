package android.mobileapp.qrcode.di.module.main;

import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.main.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class MainModule {

    private MainActivity mActivity;

    public MainModule(MainActivity activity) {
        mActivity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity() {
        return mActivity;
    }
}
