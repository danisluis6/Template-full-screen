package android.mobileapp.qrcode.view.activity.main;

import android.content.Context;
import android.mobileapp.qrcode.data.storage.entities.Content;
import android.widget.Toast;

import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private MainModel mMainModel;
    private MainView mMainView;
    private Context mContext;
    private MainActivity mActivity;

    public MainPresenterImpl(Context context, MainActivity activity, MainView mainView, MainModel mainModel) {
        mMainView = mainView;
        mMainModel = mainModel;
        mContext = context;
        mActivity = activity;
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

    @Override
    public void deleteContent(final Content content) {
        if (mMainView != null) {
            mMainView.showDialogProgress();
            mMainModel.deleteContent(content);
        }
    }

    @Override
    public void deleteContentSuccess(final String messasge) {
        if (mMainView != null) {
            mMainView.dismissDialogProgress();
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, messasge, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void deleteContentFailure(final String message) {
        if (mMainView != null) {
            mMainView.dismissDialogProgress();
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
