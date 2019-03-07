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
    }

    public final static int REQUEST_GALLERY = 4;

    public final static int REQUEST_STORAGE = 2;

    static int DOUBLE_CLICK_TIME_DELTA = 500;

    public static final String PATTERN_FILE_TYPE = ".*\\.(jpg|jpeg|gif|png|bmp|pdf|ps|doc|docx|dot|wps|wpd|odt|rtf|xls|xlsx|ppt|pptx|ods|csv|htm|html|tif|tiff|pcl|txt|JPG|JPEG|GIF|PNG|BMP|PDF|PS|DOC|DOCX|DOT|WPS|WPD|ODT|RTF|XLS|XLSX|PPT|PPTX|ODS|CSV|HTM|HTML|TIF|TIFF|PCL|TXT)";

    public static final String INTERNAL_STORAGE = "Internal Storage";

    public static final float MAX_SCALE = 3f;
    public static final float MID_SCALE = 1.5f;
    public static final float MIN_SCALE = 1f;
}
