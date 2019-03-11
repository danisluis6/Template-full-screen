package android.mobileapp.qrcode.view.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.mobileapp.qrcode.data.storage.dao.ContentDAO;
import android.mobileapp.qrcode.data.storage.entities.Content;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.service.connect.ApiService;
import android.mobileapp.qrcode.service.connect.rx.DisposableManager;
import android.os.AsyncTask;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainModelImpl implements MainModel {

    private Context mContext;
    private ApiService mApiService;
    private ContentDAO mContentDAO;
    private MainPresenter mMainPresenter;

    public MainModelImpl(Context context, ApiService apiService, ContentDAO contentDAO) {
        mContext = context;
        mApiService = apiService;
        mContentDAO = contentDAO;
    }

    @Override
    public void attachPresenter(MainPresenterImpl mainPresenter) {
        mMainPresenter = mainPresenter;
    }

    @Override
    public void saveContent(final Content content) {
        AsyncTask.execute(new Runnable() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void run() {
                if (mContentDAO.saveItem(content) > 0) {
                    getAllHistory();
                } else {
                    mMainPresenter.saveContentFailure(mContext.getString(R.string.error_save));
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllHistory() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                mContentDAO.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Content>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<Content> contents) throws Exception {
                        mMainPresenter.getContentSuccess(contents);
                    }
                });
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void deleteContent(final Content content) {
        AsyncTask.execute(new Runnable() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void run() {
                if(mContentDAO.deleteItems(content) > 0) {
                    mMainPresenter.deleteContentSuccess(mContext.getString(R.string.delete_content_success));
                } else {
                    mMainPresenter.deleteContentFailure(mContext.getString(R.string.delete_content_failure));
                }
            }
        });
    }
}
