package android.mobileapp.qrcode.view.activity.main;


import android.content.Context;
import android.mobileapp.qrcode.data.storage.dao.ContentDAO;
import android.mobileapp.qrcode.service.connect.ApiService;
import android.mobileapp.qrcode.service.connect.rx.DisposableManager;

public class MainModelImpl implements MainModel {

    private Context mContext;
    private ApiService mApiService;
    private ContentDAO mContentDAO;
    private MainPresenter mMainPresenter;
    private DisposableManager mDisposableManager;

    public MainModelImpl(Context context, ApiService apiService, ContentDAO contentDAO, DisposableManager disposableManager) {
        mContext = context;
        mApiService = apiService;
        mContentDAO = contentDAO;
        mDisposableManager = disposableManager;
    }

    @Override
    public void attachPresenter(MainPresenterImpl mainPresenter) {
        mMainPresenter = mainPresenter;
    }
}
