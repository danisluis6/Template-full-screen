package android.mobileapp.qrcode.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.mobileapp.qrcode.di.component.AppComponent;
import android.mobileapp.qrcode.di.component.DaggerAppComponent;
import android.mobileapp.qrcode.di.module.app.AppModule;
import android.os.Bundle;

public class Application extends android.app.Application {

    private Context mContext;
    private static Application sInstance;
    private AppComponent mApplicationComponent;

    public static synchronized Application getInstance() {
        if (sInstance == null) {
            sInstance = new Application();
        }
        return sInstance;
    }

    private void initAppComponent() {
        mApplicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this,mContext))
                .build();
    }

    public AppComponent getAppComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sInstance = this;
        initAppComponent();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
