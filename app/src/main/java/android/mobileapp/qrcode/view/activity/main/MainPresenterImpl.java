package android.mobileapp.qrcode.view.activity.main;

import android.mobileapp.qrcode.data.storage.entities.Content;

import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private MainModel mMainModel;
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView, MainModel mainModel) {
        mMainView = mainView;
        mMainModel = mainModel;
        mMainModel.attachPresenter(this);
    }

    @Override
    public void saveContent(Content content) {
        if (mMainView != null) {
            mMainView.showDialogProgress();
            mMainModel.saveContent(content);
        }
    }

    @Override
    public void saveContentFailure(String message) {
        if (mMainView != null) {
            mMainView.dismissDialogProgress();
            mMainView.saveContentFailure(message);
        }
    }

    @Override
    public void saveContentSuccess(Content content) {
        if (mMainView != null) {
            mMainView.dismissDialogProgress();
            mMainView.saveContentSuccess(content);
        }
    }

    @Override
    public void getAllHistory() {
        if (mMainView != null) {
            mMainView.showDialogProgress();
            mMainModel.getAllHistory();
        }
    }

    @Override
    public void getContentSuccess(List<Content> contents) {
        if (mMainView != null) {
            mMainView.dismissDialogProgress();
            mMainView.getContentSuccess(contents);
        }
    }
}
