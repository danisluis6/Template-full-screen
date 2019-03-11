package android.mobileapp.qrcode.custom;

import android.mobileapp.qrcode.scan.R;

import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;

public class BuilderManager {

    private static int[] imageResources = new int[]{
            R.drawable.ic_email_,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,
            R.drawable.cat,
            R.drawable.deer,
            R.drawable.dolphin,
            R.drawable.eagle,
            R.drawable.horse,
            R.drawable.elephant,
            R.drawable.owl,
            R.drawable.peacock,
            R.drawable.pig,
            R.drawable.rat,
            R.drawable.snake,
            R.drawable.squirrel
    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    public static SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
        return new SimpleCircleButton.Builder()
                .normalImageRes(getImageResource())
                .pieceColorRes(R.color.default_bmb_dot_color);
    }

    private BuilderManager() {
    }

}
