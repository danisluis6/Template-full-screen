package android.mobileapp.qrcode.service.response.configure;

import android.mobileapp.qrcode.data.storage.entities.Configure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.mobileapp.qrcode.service.response.Error;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

public class ConfigureResponse {

    @SerializedName("error")
    @Expose
    private Error error;

    @SerializedName("data")
    @Expose
    private Configure data;

    public Error getError() {
        return error;
    }

    public Configure getData() {
        return data;
    }

}
