package android.mobileapp.qrcode.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mobileapp.qrcode.helper.QRProtocol;
import android.mobileapp.qrcode.helper.Utils;
import android.mobileapp.qrcode.scan.OverrideFonts;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

public class QRCodeDialog extends DialogFragment implements View.OnClickListener {

    private MainActivity mActivity;
    private Context mContext;

    private TextView tvContentData, tvTitle;
    private String contentData;
    private ImageView imvExit, imvLink, imvQRCode, imvSearch, imvGenerateBitmap;

    @Inject
    public QRCodeDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_qrcode, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        initComponent(view);
        if (getArguments() != null) {
            contentData = getArguments().getString(QRProtocol.QR_DIALOG);
        }
        tvContentData.setText(contentData);
        if(Utils.matcherURL(contentData)) {
            imvLink.setVisibility(View.VISIBLE);
            imvSearch.setVisibility(View.GONE);
        } else {
            imvLink.setVisibility(View.GONE);
            imvSearch.setVisibility(View.VISIBLE);
        }
        imvExit.setOnClickListener(this);
        imvQRCode.setOnClickListener(this);
        imvSearch.setOnClickListener(this);
        return view;
    }

    private void initComponent(View view) {
        tvContentData = view.findViewById(R.id.tvContentData);
        imvExit = view.findViewById(R.id.imvExit);
        imvLink = view.findViewById(R.id.imvLink);
        imvQRCode = view.findViewById(R.id.imvQRCode);
        imvSearch = view.findViewById(R.id.imvSearch);
        imvGenerateBitmap = view.findViewById(R.id.imvGenerateBitmap);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
    }

    public void setParentFragment(Context context, MainActivity activity) {
        mActivity = activity;
        mContext = context;
    }

    public interface QRCodeInterface {
        void exitQRDialog();
    }

    private QRCodeInterface mQRQrCodeInterface;

    public void attachInterface(QRCodeInterface _interface) {
        mQRQrCodeInterface = _interface;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imvExit:
                this.dismiss();
                mQRQrCodeInterface.exitQRDialog();
                break;
            case R.id.imvQRCode:
                generateCodeFromContent(contentData);
                break;
            case R.id.imvSearch:
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(contentData, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }

    private void generateCodeFromContent(String contentData) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix matrix;
        try {
            matrix = multiFormatWriter.encode(contentData, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(matrix);
            imvGenerateBitmap.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
