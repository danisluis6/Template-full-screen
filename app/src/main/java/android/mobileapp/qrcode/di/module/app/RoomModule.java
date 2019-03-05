package android.mobileapp.qrcode.di.module.app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.mobileapp.qrcode.app.Application;
import android.mobileapp.qrcode.data.AppDatabase;
import android.mobileapp.qrcode.data.storage.dao.ContentDAO;
import android.mobileapp.qrcode.service.connect.ApiService;
import android.mobileapp.qrcode.service.connect.rx.DisposableManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class RoomModule {

    private AppDatabase mAppDatabase;
    private Context mContext;

    public RoomModule(Application Application, Context context) {
        mContext = context;
        mAppDatabase = Room.databaseBuilder(Application, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase() {
        return mAppDatabase;
    }

    @Singleton
    @Provides
    DisposableManager provideDisposableManager() {
        return new DisposableManager();
    }

    @Singleton
    @Provides
    ContentDAO provideContentDAO() {
        return mAppDatabase.getContentDAO();
    }

    @Singleton
    @Provides
    MainModel provideMainModel(ApiService apiService) {
        return new MainModelImpl(mContext, apiService);
    }
}
