package android.mobileapp.qrcode.helper;

import android.graphics.Canvas;
import android.mobileapp.qrcode.view.dialog.adapter.HistoryAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener mListener;
    private final String TAG_NAME;
    private int mCount = 0;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener, String tagName) {
        super(dragDirs, swipeDirs);
        this.mListener = listener;
        TAG_NAME = tagName;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        ++mCount;
        View foregroundView = null;
        switch (TAG_NAME) {
            case Constants.DIALOG_FRAGMENT_TAG_HISTORY:
                foregroundView = ((HistoryAdapter.MyViewHolder) viewHolder).viewForeground;
                break;
        }
        getDefaultUIUtil().onSelected(foregroundView);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        View foregroundView = null;
        switch (TAG_NAME) {
            case Constants.DIALOG_FRAGMENT_TAG_HISTORY:
                foregroundView = ((HistoryAdapter.MyViewHolder) viewHolder).viewForeground;
                break;
        }
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        mCount = 0;
        View foregroundView = null;
        switch (TAG_NAME) {
            case Constants.DIALOG_FRAGMENT_TAG_HISTORY:
                foregroundView = ((HistoryAdapter.MyViewHolder) viewHolder).viewForeground;
                break;
        }
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        View backgroundView = null;
        switch (TAG_NAME) {
            case Constants.DIALOG_FRAGMENT_TAG_HISTORY:
                backgroundView = ((HistoryAdapter.MyViewHolder) viewHolder).viewBackground;
                break;
        }
        getDefaultUIUtil().onDraw(c, recyclerView, backgroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (mCount > 1) return 0;
        return super.getMovementFlags(recyclerView, viewHolder);
    }


    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}