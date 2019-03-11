package android.mobileapp.qrcode.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mobileapp.qrcode.data.storage.entities.Content;
import android.mobileapp.qrcode.helper.AlertDialog;
import android.mobileapp.qrcode.helper.QRProtocol;
import android.mobileapp.qrcode.helper.RecyclerItemTouchHelper;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.activity.main.MainActivity;
import android.mobileapp.qrcode.view.activity.main.MainPresenter;
import android.mobileapp.qrcode.view.dialog.adapter.HistoryAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

public class QRHistory extends DialogFragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private MainActivity mActivity;
    private Context mContext;
    private HistoryAdapter mHistoryAdapter;
    private TextView tvTitleHistory;
    private ImageView imvClose;
    private int mDeletedIndex;
    private RecyclerView rcvHistory;
    private AlertDialog mAlertDialogConfirm;
    private Content mDeletedItem;
    private MainPresenter mPresenter;
    private QRHistoryInterface mHistoryInterface;

    @Inject
    public QRHistory() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_history, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        initComponent(view);
        if (getArguments() != null) {
            if (getArguments().getInt(QRProtocol.OBJ) == 0) {
                Content content = getArguments().getParcelable(QRProtocol.OBJ_CONTENT);
                mHistoryAdapter.insertObj(content);
            } else {
                ArrayList<Content> contents = getArguments().getParcelableArrayList(QRProtocol.ARR_CONTENT);
                mHistoryAdapter.updateQRHistory(contents);
            }
        }
        return view;
    }

    private void initComponent(View view) {
        rcvHistory = view.findViewById(R.id.rcvHistory);
        tvTitleHistory = view.findViewById(R.id.tvTitleHistory);
        tvTitleHistory.setText(getString(R.string.label_history));
        imvClose = view.findViewById(R.id.imvClose);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        rcvHistory.setHasFixedSize(true);
        rcvHistory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mHistoryAdapter = new HistoryAdapter(mContext, new ArrayList<Content>(), new HistoryAdapter.HistoryInterface() {
            @Override
            public void getContent(Content item) {
                mHistoryInterface.getContent(item);
            }
        });
        rcvHistory.setAdapter(mHistoryAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rcvHistory);
    }

    public interface QRHistoryInterface {
        void getContent(Content content);
    }

    public void setParentFragment(Context context, MainActivity activity, MainPresenter presenter, QRHistoryInterface _interface) {
        mActivity = activity;
        mContext = context;
        mPresenter = presenter;
        mHistoryInterface = _interface;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof HistoryAdapter.ViewHolder) {
            mDeletedIndex = viewHolder.getAdapterPosition();
            mDeletedItem = mHistoryAdapter.getGroupContents().get(viewHolder.getAdapterPosition());
            mHistoryAdapter.remove(mDeletedIndex);

            if (mAlertDialogConfirm == null) {
                mAlertDialogConfirm = new AlertDialog().createDialog(getString(R.string.confirm_delete_messages), getString(R.string.btn_delete), getString(R.string.btn_cancel));
                mAlertDialogConfirm.setOnPositiveListener(new AlertDialog.OnPositiveListener() {
                    @Override
                    public void onPositiveListener() {
                        mPresenter.deleteContent(mDeletedItem);
                    }
                });
                mAlertDialogConfirm.setOnNegativeListener(new AlertDialog.OnNegativeListener() {
                    @Override
                    public void onNegativeListener() {
                        mHistoryAdapter.restore(mDeletedIndex, mDeletedItem);
                        if (mDeletedIndex == 0) {
                            rcvHistory.scrollToPosition(mDeletedIndex);
                        }
                    }
                });
            }
            if (!mAlertDialogConfirm.isAdded()) {
                mAlertDialogConfirm.show(mActivity.getSupportFragmentManager(), QRProtocol.QR_CONFIRM);
            }
        }
    }
}
