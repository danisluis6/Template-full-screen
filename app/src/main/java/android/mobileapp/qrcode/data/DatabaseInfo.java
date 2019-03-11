package android.mobileapp.qrcode.data;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */
public class DatabaseInfo {

    static final int DB_VERSION = 1;

    static final String DB_NAME = "qrcode";

    public static class Tables {
        public static final String Content = "content";
    }

    public static class Content {
        public static final String COLUMN_CONTENT_ID = "contentID";
        public static final String COLUMN_CONTENT_TYPE = "contentType";
        public static final String COLUMN_CONTENT_DATA = "contentData";
        public static final String COLUMN_CONTENT_QRCODE = "contentQRCode";
        public static final String COLUMN_CONTENT_DATE = "contentDate";
        public static final String COLUMN_CONTENT_USER_ID = "userID";
    }

    public static class Configuration {
        public static final String COLUMN_MININUM_VERSION = "android-min-version";
        public static final String COLUMN_NEWEST_VERSION = "android-latest-version";
    }
}
