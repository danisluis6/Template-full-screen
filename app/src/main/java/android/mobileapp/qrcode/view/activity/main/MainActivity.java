package android.mobileapp.qrcode.view.activity.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.mobileapp.qrcode.app.Application;
import android.mobileapp.qrcode.custom.BuilderManager;
import android.mobileapp.qrcode.data.storage.entities.Content;
import android.mobileapp.qrcode.di.module.main.MainModule;
import android.mobileapp.qrcode.helper.Config;
import android.mobileapp.qrcode.helper.Constants;
import android.mobileapp.qrcode.helper.QRProtocol;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.BaseActivity;
import android.mobileapp.qrcode.view.dialog.QRCodeDialog;
import android.mobileapp.qrcode.view.dialog.QRHistory;
import android.mobileapp.qrcode.view.dialog.QRWebView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.Result;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.List;

import javax.inject.Inject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends BaseActivity implements ZXingScannerView.ResultHandler, MainView {

    @Inject
    Context mContext;

    @Inject
    MainActivity mActivity;

    @Inject
    QRCodeDialog mQrCodeDialog;

    @Inject
    QRWebView mQrWebView;

    @Inject
    QRHistory mQRHistory;

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    public ProgressDialog pgDialog;

    private static final int UI_ANIMATION_DELAY = 0;
    private final Handler mHideHandler = new Handler();

    private static final int REQUEST_CAMERA = 1;

    private ZXingScannerView scannerView;
    private BoomMenuButton mBoomMenuButton;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            scannerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    @Override
    public void distributedDaggerComponents() {
        Application.getInstance().getAppComponent()
                .plus(new MainModule(this, this))
                .inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initAttributes() {
        scannerView = findViewById(R.id.zXingScannerView);
        mBoomMenuButton = findViewById(R.id.bottomMenu);
        mQrCodeDialog.setParentFragment(mContext, mActivity);
        mQrCodeDialog.attachQRHistory(mQRHistory);
        mQrCodeDialog.attachQRWebview(mQrWebView, this);
        mQrCodeDialog.attachInterface(new QRCodeDialog.QRCodeInterface() {
            @Override
            public void exitQRDialog() {
                delayedHide(0);
                if (checkPermission()) {
                    if (scannerView == null) {
                        scannerView = new ZXingScannerView(MainActivity.this);
                        setContentView(scannerView);
                    }
                    scannerView.setResultHandler(MainActivity.this);
                    scannerView.startCamera();
                }
            }
        });
    }

    @Override
    protected void initViews() {
        initMenu();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarGradient(this);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                launchApplication();
                delayedHide(0);
            } else {
                requestPermissions();
            }
        }
    }

    private void initMenu() {
        mBoomMenuButton.setButtonEnum(ButtonEnum.SimpleCircle);
        mBoomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        mBoomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        for (int i = 0; i < mBoomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = BuilderManager.getSimpleCircleButtonBuilder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (index == Config._OP_SCANNER_QR_CODE) {
                                // TODO
                            } else if (index == Config._OP_GALLERY) {
                                // TODO
                            }
                        }
                    });
            mBoomMenuButton.addBuilder(builder);
        }

    }

    private void launchApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(MainActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private  void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.custom_bg);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
            window.setBackgroundDrawable(background);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(0);
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        launchApplication();
                    } else {
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        FragmentManager fm = mActivity.getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(QRProtocol.QR_DIALOG, scanResult);
        mQrCodeDialog.setArguments(bundle);
        mQrCodeDialog.show(fm, Constants.FRG_DIALOG_TAG.DIALOG_QRCODE);
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    @Override
    public void showDialogProgress() {
        if (pgDialog != null && !pgDialog.isShowing()) {
            pgDialog.setTitle(null);
            pgDialog.setCanceledOnTouchOutside(false);
            pgDialog.setMessage(mContext.getResources().getString(R.string.progress_dialog_waitting));
            try {
                pgDialog.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void dismissDialogProgress() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pgDialog != null && pgDialog.isShowing()) {
                    pgDialog.dismiss();
                }
            }
        });
    }


    /**
     * API
     * @param content
     */
    @Override
    public void saveContent(Content content) {
        mMainPresenter.saveContent(content);
    }

    @Override
    public void saveContentFailure(final String message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void saveContentSuccess(final Content content) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mQrCodeDialog.updateContent(content);
            }
        });
    }

    @Override
    public void getAllHistory() {
        mMainPresenter.getAllHistory();
    }

    @Override
    public void getContentSuccess(final List<Content> contents) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mQrCodeDialog.loadContents(contents);
            }
        });
    }
}