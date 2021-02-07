package android.mobileapp.qrcode.di.component;

import android.mobileapp.qrcode.di.module.app.AppModule;
import android.mobileapp.qrcode.di.module.app.RoomModule;
import android.mobileapp.qrcode.di.module.main.MainModule;
import android.mobileapp.qrcode.service.connect.FactoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                RoomModule.class,
                FactoryModule.class
        }
)
public interface AppComponent {
        MainComponent plus(MainModule module);
}
