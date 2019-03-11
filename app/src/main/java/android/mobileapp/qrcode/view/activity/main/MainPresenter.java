package android.mobileapp.qrcode.view.activity.main;

import android.mobileapp.qrcode.data.storage.entities.Content;

import java.util.List;

public interface MainPresenter {
    void saveContent(Content content);

    void saveContentFailure(String string);

    void getAllHistory();

    void getContentSuccess(List<Content> contents);

    void deleteContent(Content mDeletedItem);

    void deleteContentSuccess(String messasge);

    void deleteContentFailure(String message);
}
