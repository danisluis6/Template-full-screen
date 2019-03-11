package android.mobileapp.qrcode.view.activity.main;

import android.mobileapp.qrcode.data.storage.entities.Content;

public interface MainModel {
    void attachPresenter(MainPresenterImpl mainPresenter);

    void saveContent(Content content);

    void getAllHistory();

    void deleteContent(Content content);
}
