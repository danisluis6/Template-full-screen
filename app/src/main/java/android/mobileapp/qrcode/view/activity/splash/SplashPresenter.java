package android.mobileapp.qrcode.view.activity.splash;

import android.mobileapp.qrcode.data.storage.entities.Configure;

import io.reactivex.disposables.Disposable;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface SplashPresenter {

  void setDisposable(Disposable disposable);

  void loadApplicationSettings();

  void loadConfigureFailure(String error);

  void loadConfigureSuccess(Configure data);
}
