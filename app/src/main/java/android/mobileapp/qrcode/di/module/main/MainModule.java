package android.mobileapp.qrcode.di.module.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.mobileapp.qrcode.di.scope.ActivityScope;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.mobileapp.qrcode.view.activity.main.MainModel;
import android.mobileapp.qrcode.view.activity.main.MainPresenter;
import android.mobileapp.qrcode.view.activity.main.MainPresenterImpl;
import android.mobileapp.qrcode.view.activity.main.MainView;
import android.mobileapp.qrcode.view.dialog.QRCodeDialog;
import android.mobileapp.qrcode.view.dialog.QRHistory;
import android.mobileapp.qrcode.view.dialog.QRWebView;
import android.mobileapp.qrcode.view.dialog.adapter.QRPDFViewFile;

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
        return new MainPresenterImpl(context,activity, mMainView, mainModel);
    }

    @ActivityScope
    @Provides
    QRWebView provideQRWebView() {
        return new QRWebView();
    }

    @ActivityScope
    @Provides
    QRCodeDialog provideQRCodeDialog() {
        return new QRCodeDialog();
    }

    @ActivityScope
    @Provides
    QRHistory provideQRHistory() {
        return new QRHistory();
    }

    @ActivityScope
    @Provides
    QRPDFViewFile provideQRPDFViewFile() {
        return new QRPDFViewFile();
    }

    @Provides
    @ActivityScope
    ProgressDialog provideProgressDialog(MainActivity activity) {
        return new ProgressDialog(activity);
    }
}
