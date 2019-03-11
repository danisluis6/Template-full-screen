package android.mobileapp.qrcode.view.dialog.adapter;

import android.content.Context;
import android.mobileapp.qrcode.data.storage.entities.Content;
import android.mobileapp.qrcode.scan.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<Content> mGroupContents;

    public void updateQRHistory(List<Content> arrContents) {
        mGroupContents = arrContents;
        notifyDataSetChanged();
    }

    public void insertObj(Content content) {
        mGroupContents.add(content);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvData, tvDate;
        public final LinearLayout viewForeground;

        ViewHolder(View view) {
            super(view);
            tvData = view.findViewById(R.id.tvData);
            tvDate = view.findViewById(R.id.tvDate);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public HistoryAdapter(Context context, List<Content> groupContents) {
        mContext = context;
        mGroupContents = groupContents;
    }

    public void remove(int index) {
        if (getItemCount() > 0 && index > -1) {
            mGroupContents.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }

    public List<Content> getGroupContents() {
        return mGroupContents;
    }

    public void restore(int index, Content item) {
        mGroupContents.add(index, item);
        notifyItemInserted(index);
        notifyItemRangeChanged(index, getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Content item = mGroupContents.get(position);
        holder.tvData.setText(item.getContentData());
        holder.tvDate.setText(item.getContentDate());
    }

    @Override
    public int getItemCount() {
        return mGroupContents.size();
    }
}
