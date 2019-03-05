package android.mobileapp.qrcode.di.module.main;

import android.content.Context;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.mobileapp.qrcode.view.activity.main.MainModel;
import android.mobileapp.qrcode.view.activity.main.MainPresenter;
import android.mobileapp.qrcode.view.activity.main.MainPresenterImpl;
import android.mobileapp.qrcode.view.activity.main.MainView;
import android.mobileapp.qrcode.view.dialog.QRCodeDialog;

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
    private MainView mMainView;

    public MainModule(MainActivity activity, MainView mainView) {
        mActivity = activity;
        mMainView = mainView;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity() {
        return mActivity;
    }

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(Context context, MainActivity activity, MainModel mainModel) {
        return new MainPresenterImpl(mMainView, mainModel);
    }

    @ActivityScope
    @Provides
    QRCodeDialog provideQRCodeDialog() {
        return new QRCodeDialog();
    }
}
