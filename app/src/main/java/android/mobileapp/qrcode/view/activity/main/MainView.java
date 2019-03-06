package android.mobileapp.qrcode.view.activity.main;

import android.mobileapp.qrcode.data.storage.entities.Content;

import java.util.List;

public interface MainView {
    void showDialogProgress();

    void dismissDialogProgress();

    void saveContent(Content content);

    void saveContentFailure(String message);

    void saveContentSuccess(Content content);

    void getAllHistory();

    void getContentSuccess(List<Content> contents);
}
