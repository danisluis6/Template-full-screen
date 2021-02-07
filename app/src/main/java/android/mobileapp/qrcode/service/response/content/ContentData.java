package android.mobileapp.qrcode.service.response.content;

import android.mobileapp.qrcode.data.storage.entities.Content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContentData {

    @SerializedName("levels")
    @Expose
    private ArrayList<Content> contents;

    public ArrayList<Content> getAll() {
        return contents;
    }
}
