package android.mobileapp.qrcode.helper;

/**
 *
 * Created by com.lorence on 03/11/2017.
 */

public class Constants {
    public static final String EMPTY_STRING = "";

    public static class FRG_DIALOG_TAG {
        public static String DIALOG_QRCODE = "Dialog QRCode";
        public static String DIALOG_QR_PDF = "Dialog QRPDF";

        public static String DIALOG_QRWEBVIEW = "Dialog QRWebView";
        public static String DIALOG_QRHISTORY = "Dialog QRHistory";

        public static String DIALOG_OP_EMAIL = "Email Generate";
    }

    public final static int REQUEST_GALLERY = 4;

    public final static int REQUEST_STORAGE = 2;

    static int DOUBLE_CLICK_TIME_DELTA = 500;

    public static final float MAX_SCALE = 3f;
    public static final float MID_SCALE = 1.5f;
    public static final float MIN_SCALE = 1f;


    public static final String DIALOG_FRAGMENT_TAG_HISTORY = "History";

    public static final String URL_APP_MARKET = "market://details?id=";
    public static final String URL_APP_STORE = "https://play.google.com/store/apps/details?id=";
}
