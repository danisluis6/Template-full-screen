package android.mobileapp.qrcode.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mobileapp.qrcode.helper.QRProtocol;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

public class QRWebView extends DialogFragment {

    private MainActivity mActivity;
    private Context mContext;

    private WebView wbLinkWebsite;

    @Inject
    public QRWebView() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_webview, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        initComponent(view);
        if (getArguments() != null) {
            loadContentOnWebview(getArguments().getString(QRProtocol.QR_WEBVIEW));
        }
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadContentOnWebview(String url) {
        wbLinkWebsite.getSettings().setJavaScriptEnabled(true);
        wbLinkWebsite.setWebViewClient(new WebViewClient());
        wbLinkWebsite .loadUrl(url);
    }

    private void initComponent(View view) {
        wbLinkWebsite = view.findViewById(R.id.wbLinkWebsite);
    }

    public void setParentFragment(Context context, MainActivity activity) {
        mActivity = activity;
        mContext = context;
    }
}
