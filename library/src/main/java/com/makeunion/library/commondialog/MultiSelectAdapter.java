package com.makeunion.library.commondialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeunion.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 *
 * @author renjialiang
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.ItemViewHolder> {

    private Context mContext;

    private List<String> mData;

    private LayoutInflater mInflater;

    private ItemSelectListener mListener;

    private List<Integer> mSelection;

    public MultiSelectAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
        mSelection = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void preSelect(List<Integer> selection) {
        mSelection = selection;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_select, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.itemTxt.setText(mData.get(position));
        holder.dividerView.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        holder.selectImg.setVisibility(mSelection.contains(position) ? View.VISIBLE : View.GONE);
        if (mData.size() == 1) {
            holder.rootView.setBackgroundResource(R.drawable.selector_all_item_btn);
        } else {
            if (position == 0) {
                holder.rootView.setBackgroundResource(R.drawable.selector_top_item_btn);
            } else if (position == mData.size() - 1) {
                holder.rootView.setBackgroundResource(R.drawable.selector_bottom_item_btn);
            } else {
                holder.rootView.setBackgroundResource(R.drawable.selector_middle_item_btn);
            }
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelection.contains(position)) {
                    mSelection.remove(new Integer(position));
                } else {
                    mSelection.add(new Integer(position));
                }
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.onItemSelected(mSelection);
                }
            }
        });
    }

    public void setListener(ItemSelectListener listener) {
        mListener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        View rootView;

        View dividerView;

        TextView itemTxt;

        ImageView selectImg;

        public ItemViewHolder(View view) {
            super(view);
            rootView = view;
            dividerView = view.findViewById(R.id.divider_view);
            itemTxt = (TextView)view.findViewById(R.id.txt);
            selectImg = (ImageView)view.findViewById(R.id.select_img);
        }
    }

    public interface ItemSelectListener {
        void onItemSelected(List<Integer> selection);
    }
}
