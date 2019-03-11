package android.mobileapp.qrcode.view.activity.splash;

import android.content.Context;
import android.content.res.Resources;
import android.mobileapp.qrcode.data.storage.entities.Configure;

import io.reactivex.disposables.Disposable;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

public class SplashPresenterImpl implements SplashPresenter {

  private Context mContext;
  private SplashActivity mActivity;
  private SplashView mSplashView;
  private SplashModel mSplashModel;

  public SplashPresenterImpl(Context context, SplashActivity activity, SplashView view, SplashModel splashModel) {
    super();
    mContext = context;
    mActivity = activity;
    mSplashView = view;
    mSplashModel = splashModel;
    mSplashModel.attachPresenter(this);
  }

  @Override
  public void loadApplicationSettings() {
    mSplashModel.loadApplicationSettings();
  }

  @Override
  public void loadConfigureFailure(String error) {
    mSplashView.loadConfigureFailure(error);
  }

  @Override
  public void loadConfigureSuccess(Configure item) {
    mSplashView.loadConfigureSuccess(item);
  }

  @Override
  public void setDisposable(Disposable disposable) {
    mSplashView.setDisposable(disposable);
  }

}
