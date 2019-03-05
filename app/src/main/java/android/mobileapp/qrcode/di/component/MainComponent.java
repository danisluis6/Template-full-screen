package android.mobileapp.qrcode.di.component;

import android.mobileapp.qrcode.di.module.main.MainModule;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.main.MainActivity;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(

        modules = {
                MainModule.class
        }
)
public interface MainComponent {
    MainActivity inject(MainActivity activity);
}
