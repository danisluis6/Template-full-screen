package android.mobileapp.qrcode.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

public class QRGenerate extends DialogFragment {

    private MainActivity mActivity;
    private Context mContext;
    private TextView tvTitle;
    private ImageView imvClose;
    private QRGenerateInterface mQrGenerateInterface;

    @Inject
    public QRGenerate() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_generate, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        initComponent(view);
        if (getArguments() != null) {
            // TODO
        }
        return view;
    }

    private void initComponent(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.label_email));
        imvClose = view.findViewById(R.id.imvClose);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mQrGenerateInterface.closeFrg();
            }
        });
    }

    public interface QRGenerateInterface {
        void closeFrg();
    }

    public void setParentFragment(Context context, MainActivity activity, QRGenerateInterface _interface) {
        mActivity = activity;
        mContext = context;
        mQrGenerateInterface = _interface;
    }

}
