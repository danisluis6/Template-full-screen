package android.mobileapp.qrcode.helper;

public class Utils {

    public static boolean matcherURL(String scannerResult) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return scannerResult.matches(regex);
    }
}
