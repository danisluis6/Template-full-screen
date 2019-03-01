package android.tutorial.com.myapplication;

import android.content.Context;
import android.graphics.Typeface;

public class OverrideFonts {

    public enum TYPE_FONT_NAME {
        ROBOTO, HELVETICANEUE
    }

    public enum TYPE_STYLE {
        BLACK, BLACK_ITATLIC, BOLD, BOLD_ITALIC, ITALIC, LIGHT, LIGHT_ITALIC, MEDIUM, MEDIUM_ITALIC, REGULAR, THIN, THIN_ITALIC, ULTRALIGHT, ULTRALIGHT_ITALIC, CONDENSEDBLACK
    }

    public static Typeface getTypeFace(Context context, TYPE_FONT_NAME _fontName, TYPE_STYLE _fontStyle) {
        switch (_fontName) {
            case ROBOTO:
               return typeFaceRobonto(context, _fontStyle);
            case HELVETICANEUE:
                return typeFaceHelveticalneue(context, _fontStyle);
        }
        return null;
    }

    private static Typeface typeFaceHelveticalneue(Context context, TYPE_STYLE _fontStyle) {
        switch (_fontStyle) {
            case BOLD:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneuebold.ttf");
            case BOLD_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneuebolditalic.ttf");
            case CONDENSEDBLACK:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneuecondensedblack.ttf");
            case ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneueitalic.ttf");
            case LIGHT:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneuelight.ttf");
            case LIGHT_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneuelightitalic.ttf");
            case MEDIUM:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneuemedium.ttf");
            case ULTRALIGHT:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneueultralight.ttf");
            case ULTRALIGHT_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneueultralightitalic.ttf");
            default:
                return Typeface.createFromAsset(context.getAssets(),"fonts/helveticaneue/helveticaneue.ttf");
        }
    }

    private static Typeface typeFaceRobonto(Context context, TYPE_STYLE _fontStyle) {
        switch (_fontStyle) {
            case BLACK:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Black.ttf");
            case BLACK_ITATLIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-BlackItalic.ttf");
            case BOLD:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Bold.ttf");
            case BOLD_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-BoldItalic.ttf");
            case ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Italic.ttf");
            case LIGHT:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Light.ttf");
            case LIGHT_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-LightItalic.ttf");
            case MEDIUM:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Medium.ttf");
            case MEDIUM_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-MediumItalic.ttf");
            case REGULAR:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Regular.ttf");
            case THIN:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Thin.ttf");
            case THIN_ITALIC:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-ThinItalic.ttf");
            default:
                return Typeface.createFromAsset(context.getAssets(),"fonts/roboto/Roboto-Black.ttf");
        }
    }

}
