package android.mobileapp.qrcode.di.component;

import android.mobileapp.qrcode.di.module.splash.SplashModule;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.splash.SplashActivity;

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
                SplashModule.class
        }
)
public interface SplashComponent {
    SplashActivity inject(SplashActivity splashActivity);
}


