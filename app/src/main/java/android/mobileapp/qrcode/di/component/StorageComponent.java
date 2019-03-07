package android.mobileapp.qrcode.di.component;

import android.mobileapp.qrcode.di.module.main.StorageModule;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.main.StorageActivity;

import dagger.Subcomponent;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@ActivityScope
@Subcomponent(

        modules = {
                StorageModule.class
        }
)
public interface StorageComponent {
    StorageActivity inject(StorageActivity storageActivity);
}


