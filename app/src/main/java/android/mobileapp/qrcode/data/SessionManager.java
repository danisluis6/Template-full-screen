package android.mobileapp.qrcode.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.mobileapp.qrcode.helper.Constants;

import javax.inject.Inject;

public class SessionManager {

    private static final String PREF_NAME = "qrcode_sharedPref";
    private static SessionManager instance;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    private static final String MINIMUM_VERSION = "minimum version";
    private static final String LATEST_VERSION = "latest version";
    private static final String DATE_UPGRADE_APP = "date upgrade app";

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

    public String getMinimumVersion() {
        return pref.getString(MINIMUM_VERSION, Constants.EMPTY_STRING);
    }

    public void setMinimumVersion(String minimumVersion) {
        editor.putString(MINIMUM_VERSION, minimumVersion);
        editor.apply();
    }

    public String getLatestVersion() {
        return pref.getString(LATEST_VERSION, Constants.EMPTY_STRING);
    }

    public void setLatestVersion(String latestVersion) {
        editor.putString(LATEST_VERSION, latestVersion);
        editor.apply();
    }

    public long getDateUpgradeApp() {
        return pref.getLong(DATE_UPGRADE_APP, 0);
    }

    public void setDateUpgradeApp(long time) {
        editor.putLong(DATE_UPGRADE_APP, time);
        editor.apply();
    }

    public void clear() {
        editor.remove(DATE_UPGRADE_APP);
        editor.apply();
    }
}
