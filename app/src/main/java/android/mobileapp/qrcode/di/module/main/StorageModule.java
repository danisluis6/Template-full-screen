package android.mobileapp.qrcode.di.module.main;

import android.content.Context;
import android.mobileapp.qrcode.data.storage.entities.Folder;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.main.StorageActivity;
import android.mobileapp.qrcode.view.activity.main.adapter.StorageAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class StorageModule {

    private StorageActivity mActivity;

    public StorageModule(StorageActivity storageActivity) {
        this.mActivity = storageActivity;
    }

    @Provides
    @ActivityScope
    StorageActivity provideStorageActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    StorageAdapter provideStorageAdapter(Context context) {
        return new StorageAdapter(context, mActivity, new ArrayList<Folder>());
    }
}
