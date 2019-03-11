package android.mobileapp.qrcode.view.activity.splash;

import android.content.Context;
import android.mobileapp.qrcode.data.SessionManager;
import android.mobileapp.qrcode.data.storage.entities.Configure;
import android.mobileapp.qrcode.helper.Utils;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.service.connect.ApiService;
import android.mobileapp.qrcode.service.connect.rx.DisposableManager;
import android.mobileapp.qrcode.service.connect.rx.IDisposableListener;
import android.mobileapp.qrcode.service.response.configure.ConfigureResponse;


/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

public class SplashModelImpl implements SplashModel {

    private Context mContext;
    private ApiService mApiService;
    private SplashPresenter mSplashPresenter;
    private DisposableManager mDisposableManager;

    public SplashModelImpl(Context context, ApiService apiService) {
        mContext = context;
        mApiService = apiService;
        mDisposableManager = new DisposableManager();
    }

    @Override
    public void attachPresenter(SplashPresenterImpl splashPresenter) {
        mSplashPresenter = splashPresenter;
    }

    @Override
    public void loadApplicationSettings() {
        if (Utils.isInternetOn(mContext)) {
            mSplashPresenter.setDisposable(mDisposableManager.getConfigure(mApiService.getConfigure(), new IDisposableListener<ConfigureResponse>() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onHandleData(ConfigureResponse response) {
                    switch (response.getError().getCode()) {
                        case 0:
                            Configure configuration = response.getData();
                            mSplashPresenter.loadConfigureSuccess(configuration);
                            saveInfoConfigureApp(configuration);
                            break;
                        default:
                            mSplashPresenter.loadConfigureFailure(mContext.getString(R.string.no_internet_connection));
                    }
                }

                @Override
                public void onRequestWrongData(int code) {

                }

                @Override
                public void onApiFailure(Throwable error) {

                }
            }));
        } else {
            mSplashPresenter.loadConfigureFailure(mContext.getString(R.string.no_internet_connection));
        }
    }

    private void saveInfoConfigureApp(Configure configure) {
        SessionManager.getInstance(mContext).setMinimumVersion(configure.getMinimumVersion());
        SessionManager.getInstance(mContext).setLatestVersion(configure.getNewestVersion());
    }
}
