package android.mobileapp.qrcode.view.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mobileapp.qrcode.custom.pdfviewer.PDFView;
import android.mobileapp.qrcode.custom.pdfviewer.listener.OnDrawListener;
import android.mobileapp.qrcode.custom.pdfviewer.listener.OnErrorListener;
import android.mobileapp.qrcode.custom.pdfviewer.listener.OnLoadCompleteListener;
import android.mobileapp.qrcode.custom.pdfviewer.listener.OnPageChangeListener;
import android.mobileapp.qrcode.helper.QRProtocol;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.mobileapp.qrcode.view.dialog.adapter.CustomZoomViewAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;

import javax.inject.Inject;

public class QRPDFView extends DialogFragment implements OnPageChangeListener {

    private MainActivity mActivity;
    private Context mContext;

    private PDFView pdfViewer;
    private FrameLayout qrViewerLayout;
    private TextView tvPdfPaging;

    private int mCurrentPage = 1;
    private int mPageCount = 0;

    @Inject
    public QRPDFView() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_pdf, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        initComponent(view);
        initPDFEvent();
        if (getArguments() != null) {
            loadPdfFile(new File(getArguments().getString(QRProtocol.QR_PDF_VIEW)));
        }
        return view;
    }

    private void initPDFEvent() {
        CustomZoomViewAdapter customZoomViewAdapter = new CustomZoomViewAdapter(mActivity, qrViewerLayout);
        customZoomViewAdapter.setGestureEventInterface(new PDFView.GestureEventInterface() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return pdfViewer.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return pdfViewer.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return pdfViewer.onDown(event);
            }

            @Override
            public void handleEndScroll(MotionEvent event) {
                pdfViewer.handleEndScroll(event);
            }
        });
    }

    private void initComponent(View view) {
        pdfViewer = view.findViewById(R.id.pdfViewer);
        tvPdfPaging = view.findViewById(R.id.tvPdfPaging);
        qrViewerLayout = view.findViewById(R.id.qrViewerLayout);
    }

    public void setParentFragment(Context context, MainActivity activity) {
        mActivity = activity;
        mContext = context;
    }

    private void loadPdfFile(File file) {
        pdfViewer.fromFile(file)
                .defaultPage(mCurrentPage)
                .enableDoubletap(false)
                .onPageChange(this)
                .swipeVertical(false)
                .showMinimap(false)
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        setupPaging();
                    }
                })
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                    }
                })
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
//                        handleMessage("Hello", "File is not valid");
                    }
                }).load();
    }

    private void setupPaging() {
        if (mPageCount > 1) {
            tvPdfPaging.setText(getString(R.string.pageCount, 1, mPageCount));
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        if (pageCount > 1) {
            tvPdfPaging.setText(getString(R.string.pageCount, page, pageCount));
        }
        mCurrentPage = page;
    }
}
