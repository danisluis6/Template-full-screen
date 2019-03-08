package android.mobileapp.qrcode.view.dialog.adapter;

import android.mobileapp.qrcode.data.storage.entities.Content;
import android.mobileapp.qrcode.scan.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<Content> mGroupContents;

    public void updateQRHistory(List<Content> arrContents) {
        mGroupContents = arrContents;
        notifyDataSetChanged();
    }

    public void insertObj(Content content) {
        mGroupContents.add(content);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvData, tvDate;
        public final View viewBackground, viewForeground;

        MyViewHolder(View view) {
            super(view);
            tvData = view.findViewById(R.id.tvData);
            tvDate = view.findViewById(R.id.tvDate);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public HistoryAdapter(List<Content> groupContents) {
        mGroupContents = groupContents;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Content item = mGroupContents.get(position);
        holder.tvData.setText(item.getContentData());
        holder.tvDate.setText(item.getContentDate());
    }

    @Override
    public int getItemCount() {
        return mGroupContents.size();
    }

    public void refresh() {
        Collections.sort(mGroupContents, new Comparator<Content>() {
            @Override
            public int compare(Content obj1, Content obj2) {
                return Integer.compare(obj2.getContentID(), obj1.getContentID());
            }
        });
        notifyDataSetChanged();
    }
}
