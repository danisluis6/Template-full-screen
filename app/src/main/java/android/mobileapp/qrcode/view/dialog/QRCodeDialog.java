package android.mobileapp.qrcode.view.dialog;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mobileapp.qrcode.data.storage.entities.Content;
import android.mobileapp.qrcode.helper.Constants;
import android.mobileapp.qrcode.helper.QRProtocol;
import android.mobileapp.qrcode.helper.Utils;
import android.mobileapp.qrcode.scan.OverrideFonts;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.mobileapp.qrcode.view.activity.main.MainView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import static android.content.Context.CLIPBOARD_SERVICE;

public class QRCodeDialog extends DialogFragment implements View.OnClickListener {

    private MainActivity mActivity;
    private Context mContext;

    private TextView tvContentData, tvTitle;
    private String contentData;
    private ImageView imvExit, imvLink, imvQRCode, imvSearch, imvGenerateBitmap, imvSave, imvHistory, imvCopy;
    private QRWebView mQrWebView;
    private QRHistory mQrHistory;

    private MainView mMainView;

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
        if (Utils.matcherURL(contentData)) {
            imvLink.setVisibility(View.VISIBLE);
            imvSearch.setVisibility(View.GONE);
        } else {
            imvLink.setVisibility(View.GONE);
            imvSearch.setVisibility(View.VISIBLE);
        }
        imvExit.setOnClickListener(this);
        imvQRCode.setOnClickListener(this);
        imvSearch.setOnClickListener(this);
        imvLink.setOnClickListener(this);
        imvSave.setOnClickListener(this);
        imvHistory.setOnClickListener(this);
        imvCopy.setOnClickListener(this);
        return view;
    }

    private void initComponent(View view) {
        tvContentData = view.findViewById(R.id.tvContentData);
        imvExit = view.findViewById(R.id.imvExit);
        imvLink = view.findViewById(R.id.imvLink);
        imvQRCode = view.findViewById(R.id.imvQRCode);
        imvSearch = view.findViewById(R.id.imvSearch);
        imvSave = view.findViewById(R.id.imvSave);
        imvHistory = view.findViewById(R.id.imvHistory);
        imvCopy = view.findViewById(R.id.imvCopy);
        imvGenerateBitmap = view.findViewById(R.id.imvGenerateBitmap);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
    }

    public void setParentFragment(Context context, MainActivity activity) {
        mActivity = activity;
        mContext = context;
    }

    public void attachQRWebview(QRWebView qrWebView, MainView mainView) {
        mQrWebView = qrWebView;
        mMainView = mainView;
        mQrWebView.setParentFragment(mContext, mActivity);
    }

    public void attachQRHistory(QRHistory qRHistory) {
        mQrHistory = qRHistory;
        mQrHistory.setParentFragment(mContext, mActivity);
    }

    public void loadContents(List<Content> arrContents) {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt(QRProtocol.OBJ, 1);
        bundle.putParcelableArrayList(QRProtocol.ARR_CONTENT, (ArrayList<? extends Parcelable>) arrContents);
        mQrHistory.setArguments(bundle);
        mQrHistory.show(fm, Constants.FRG_DIALOG_TAG.DIALOG_QRWEBVIEW);
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    public void updateContent(Content content) {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt(QRProtocol.OBJ, 0);
        bundle.putParcelable(QRProtocol.OBJ_CONTENT, content);
        mQrHistory.setArguments(bundle);
        mQrHistory.show(fm, Constants.FRG_DIALOG_TAG.DIALOG_QRWEBVIEW);
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
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
            case R.id.imvLink:
                if (imvLink.getVisibility() == View.VISIBLE) {
                    FragmentManager fm = mActivity.getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString(QRProtocol.QR_WEBVIEW, contentData);
                    mQrWebView.setArguments(bundle);
                    mQrWebView.show(fm, Constants.FRG_DIALOG_TAG.DIALOG_QRWEBVIEW);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.commit();
                }
                break;
            case R.id.imvSave:
                mMainView.saveContent(getContent());
                break;
            case R.id.imvHistory:
                mMainView.getAllHistory();
                break;
            case R.id.imvCopy:
                final ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(Constants.EMPTY_STRING, contentData);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(mContext, "Saved to Clipboard", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public Content getContent() {
        Content obj = new Content();
        obj.setContentData(contentData);
        if (Utils.matcherURL(contentData)) {
            obj.setContentType("LINK");
        } else {
            obj.setContentType("TEXT");
        }
        obj.setContentDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        obj.setContentQRCode("");
        obj.setContentUserID(0);
        return obj;
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
