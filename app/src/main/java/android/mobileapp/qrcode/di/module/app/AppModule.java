package android.mobileapp.qrcode.di.module.app;

import android.content.Context;
import android.mobileapp.qrcode.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class AppModule {

    private Application mApplication;
    private Context mContext;

    public AppModule(Application application, Context context) {
        this.mApplication = application;
        this.mContext = context;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
