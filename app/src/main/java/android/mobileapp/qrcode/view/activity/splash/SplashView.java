package android.mobileapp.qrcode.view.activity.splash;

import android.mobileapp.qrcode.data.storage.entities.Configure;

import io.reactivex.disposables.Disposable;

public interface SplashView {
    void loadConfigureSuccess(Configure item);
    void setDisposable(Disposable disposable);
    void loadConfigureFailure(String error);
}
