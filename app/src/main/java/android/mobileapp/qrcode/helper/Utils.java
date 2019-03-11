package android.mobileapp.qrcode.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.mobileapp.qrcode.data.storage.entities.Folder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static boolean matcherURL(String scannerResult) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return scannerResult.matches(regex);
    }

    public static boolean matcherImage(String scannerResult) {
        String regex = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
        return scannerResult.matches(regex);
    }

    public static boolean matcherPDF(String scannerResult) {
        String regex = "(.*/)*.+\\.(pdf|PDF)$";
        return scannerResult.matches(regex);
    }

    private static long sLastClickTime = 0;

    public static boolean isEmptyString(String s) {
        return TextUtils.equals(Constants.EMPTY_STRING, s);
    }

    public static boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - sLastClickTime < Constants.DOUBLE_CLICK_TIME_DELTA) {
            sLastClickTime = clickTime;
            return true;
        }
        sLastClickTime = clickTime;
        return false;
    }

    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        String res = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        return res;
    }

    public static String scanQRImage(Bitmap bMap) {
        String contents = null;
        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        }
        catch (Exception e) {
            Log.e("TAG", "Error decoding barcode", e);
        }
        return contents;
    }

    public static List<Folder> getFilterArrayFolder(List<Folder> folders) {
        ArrayList<Folder> folderArrayList = new ArrayList<>();
        for (int index = 0; index < folders.size(); index++) {
            if (fitterFolder(folders.get(index))) folderArrayList.add(folders.get(index));
        }
        return folderArrayList;
    }

    private static boolean fitterFolder(Folder item) {
        if (item.getName().toLowerCase().startsWith("movie")
                || item.getName().toLowerCase().startsWith("picture")
                || item.getName().toLowerCase().startsWith("zalo")
                || item.getName().toLowerCase().startsWith("download")
                || item.getName().toLowerCase().startsWith("dcim")
                || item.getName().toLowerCase().startsWith("android"))
            return true;
        return false;
    }

    public static int calculateNumberPage(File file) {
        try {
            return PDDocument.load(file).getNumberOfPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String versionToComparable( String version )
    {
        String versionNum = version;
        int at = version.indexOf( '-' );
        if ( at >= 0 )
            versionNum = version.substring( 0, at );
        String[] numAr = versionNum.split( "\\." );
        String versionFormatted = "0";
        for ( String tmp : numAr ){
            versionFormatted += String.format( "%4s", tmp ).replace(' ', '0');
        }
        while ( versionFormatted.length() < 12 )  {
            versionFormatted += "0000";
        }
        return versionFormatted;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Constants.EMPTY_STRING;
    }

    @SuppressLint("MissingPermission")
    public static boolean isInternetOn(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
