package android.mobileapp.qrcode.view.activity.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.mobileapp.qrcode.app.Application;
import android.mobileapp.qrcode.data.SessionManager;
import android.mobileapp.qrcode.data.storage.entities.Configure;
import android.mobileapp.qrcode.di.module.splash.SplashModule;
import android.mobileapp.qrcode.helper.AlertDialog;
import android.mobileapp.qrcode.helper.Constants;
import android.mobileapp.qrcode.helper.Utils;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.BaseActivity;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    Context mContext;

    @Inject
    SplashActivity mSplashActivity;

    @Inject
    SplashPresenter mSplashPresenter;


    private boolean mApiSuccess = false;
    private boolean mTimeOut = false;
    private boolean isUpgrade = false;
    private Disposable mDisposable;
    private AlertDialog mAlertDialogConfirm;

    @Override
    public void distributedDaggerComponents() {
        Application.getInstance().getAppComponent()
                .plus(new SplashModule(this, this))
                .inject(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initAttributes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarGradiant(this);
        }
    }

    @Override
    protected void initViews() {
        startApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUpgrade) {
            String currentVersionApp = Utils.versionToComparable(Utils.getVersionName(getApplicationContext()));
            String minimumVersionApp = Utils.versionToComparable(SessionManager.getInstance(this).getMinimumVersion());
            if (currentVersionApp.compareTo(minimumVersionApp) < 0) {
                forceUserUpgradeLatestVersion(true);
                return;
            }
            mApiSuccess = true;
            moveToNextActivity();
        }
    }

    private void startApplication() {
        mSplashPresenter.loadApplicationSettings();
        loadApplication();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        }
    }

    private void loadApplication() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTimeOut = true;
            }
        }, 200);
    }

    @Override
    public void loadConfigureFailure(String message) {
        AlertDialog mAlertDialogFailed = new AlertDialog().createDialog(message, getString(R.string.btn_retry), getString(R.string.btn_exit));
        mAlertDialogFailed.setOnPositiveListener(new AlertDialog.OnPositiveListener() {
            @Override
            public void onPositiveListener() {
                mSplashPresenter.loadApplicationSettings();
            }
        });
        mAlertDialogFailed.setOnNegativeListener(new AlertDialog.OnNegativeListener() {
            @Override
            public void onNegativeListener() {
                finish();
            }
        });
        mAlertDialogFailed.show(SplashActivity.this.getSupportFragmentManager(), getString(R.string.app_name));
    }

    @Override
    public void loadConfigureSuccess(Configure item) {
        String currentVersionApp = Utils.versionToComparable(Utils.getVersionName(getApplicationContext()));
        String minimumVersionApp = Utils.versionToComparable(SessionManager.getInstance(this).getMinimumVersion());
        String newestVersionApp = Utils.versionToComparable(SessionManager.getInstance(this).getLatestVersion());
        if (currentVersionApp.compareTo(minimumVersionApp) < 0) {
            forceUserUpgradeLatestVersion(true);
        } else if (currentVersionApp.compareTo(minimumVersionApp) >= 0 && currentVersionApp.compareTo(newestVersionApp) < 0) {
            forceUserUpgradeLatestVersion(false);
        } else {
            mApiSuccess = true;
            moveToNextActivity();
        }
    }

    private void forceUserUpgradeLatestVersion(boolean isForce) {
        if (isFinishing()) {
            return;
        }
        if (TextUtils.equals(Utils.getVersionName(getApplicationContext()), SessionManager.getInstance(this).getLatestVersion()) || !isTimeExpire() && !isForce) {
            mApiSuccess = true;
        } else {
            if (mAlertDialogConfirm == null) {
                mAlertDialogConfirm = new AlertDialog().createDialog(String.format(getString(R.string.warning_upgrade_new_version),
                        String.valueOf(SessionManager.getInstance(this).getLatestVersion())), getString(R.string.btn_update), !isForce ? getString(R.string.btn_dismiss) : null);
                mAlertDialogConfirm.setOnPositiveListener(new AlertDialog.OnPositiveListener() {
                    @Override
                    public void onPositiveListener() {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL_APP_MARKET + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL_APP_STORE + appPackageName)));
                        }
                        isUpgrade = true;
                    }
                });
                mAlertDialogConfirm.setOnNegativeListener(new AlertDialog.OnNegativeListener() {
                    @Override
                    public void onNegativeListener() {
                        mApiSuccess = true;
                        setTimeToOpenDialog();
                    }
                });
            }
            if (!mAlertDialogConfirm.isAdded()) {
                mAlertDialogConfirm.show(SplashActivity.this.getSupportFragmentManager(), getString(R.string.app_name));
            }
        }
    }

    private boolean isTimeExpire() {
        Date date = new Date(System.currentTimeMillis());
        long result = (date.getTime() - SessionManager.getInstance(this).getDateUpgradeApp()) / 1000;
        return result > 24 * 60 * 60;
    }

    public void setTimeToOpenDialog() {
        Date date = new Date(System.currentTimeMillis());
        SessionManager.getInstance(this).setDateUpgradeApp(date.getTime());
    }

    private void moveToNextActivity() {
        if (mTimeOut  && mApiSuccess) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void setDisposable(Disposable disposable) {
        mDisposable = disposable;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

}
