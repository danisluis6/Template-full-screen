package android.mobileapp.qrcode.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.mobileapp.qrcode.helper.Constants;

import javax.inject.Inject;

public class SessionManager {

    private static final String TEMP = "temp";

    private static final String PREF_NAME = "qrcode_sharedPref";
    private static SessionManager instance;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    @Inject
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public String getTemp() {
        return pref.getString(TEMP, Constants.EMPTY_STRING);
    }

    public void setTemp(String temp) {
        editor.putString(TEMP, temp);
        editor.apply();
    }

    public void clear() {
        editor.remove(TEMP);
        editor.apply();
    }
}
