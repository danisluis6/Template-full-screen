package android.mobileapp.qrcode.di.module.splash;


import android.content.Context;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.splash.SplashActivity;
import android.mobileapp.qrcode.view.activity.splash.SplashModel;
import android.mobileapp.qrcode.view.activity.splash.SplashPresenter;
import android.mobileapp.qrcode.view.activity.splash.SplashPresenterImpl;
import android.mobileapp.qrcode.view.activity.splash.SplashView;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    private SplashActivity mSplashActivity;
    private SplashView mSplashView;

    public SplashModule(SplashActivity splashActivity, SplashView splashView) {
        this.mSplashActivity = splashActivity;
        this.mSplashView = splashView;
    }

    @Provides
    @ActivityScope
    SplashActivity provideSplashActivity() {
        return mSplashActivity;
    }

    @Provides
    @ActivityScope
    SplashView provideSplashView() { return mSplashView; }

    @Provides
    @ActivityScope
    SplashPresenter provideSplashPresenter(Context context, SplashModel model) {
        return new SplashPresenterImpl(context, mSplashActivity, mSplashView, model);
    }
}
