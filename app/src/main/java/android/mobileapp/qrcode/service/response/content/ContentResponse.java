package android.mobileapp.qrcode.service.response.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentResponse {

    @SerializedName("error")
    @Expose
    private Error error;

    @SerializedName("data")
    @Expose
    private ContentData data;

    public Error getError() {
        return error;
    }

    public ContentData getData() {
        return data;
    }

}
