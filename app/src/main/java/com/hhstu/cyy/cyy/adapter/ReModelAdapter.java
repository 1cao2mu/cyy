package com.hhstu.cyy.cyy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;
import com.hhstu.cyy.cyy.utils.Constants;
import com.hhstu.cyy.cyy.view.CircleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/13.
 */
public class ReModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    JSONArray array;
    Context context;
    OnItemChildViewClickListener onItemChildViewClickListener;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public ReModelAdapter(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View item = LayoutInflater.from(context).inflate(R.layout.item_re_model, null);
            ItemViewHolder viewHolder = new ItemViewHolder(item);
            viewHolder.tv_name = (TextView) item.findViewById(R.id.tv_name);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View item = LayoutInflater.from(context).inflate(R.layout.item_model_footer_se, null);
            FooterViewHolder viewHolder = new FooterViewHolder(item);
            viewHolder.cp_progress = (CircleProgressBar) item.findViewById(R.id.cp_progress);
            viewHolder.tv_all_ed = (TextView) item.findViewById(R.id.tv_all_ed);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        try {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder Mholder = (ItemViewHolder) holder;
                JSONObject object = array.getJSONObject(position);
                Mholder.tv_name.setText(object.getString("Name"));
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder Mholder = (FooterViewHolder) holder;
                if (!arrayIsNull(array) && array.length() %Constants .PAGE_SIZE == 0 && position % Constants.PAGE_SIZE != 1) {
                    Mholder.cp_progress.setVisibility(View.VISIBLE);
                    Mholder.tv_all_ed.setVisibility(View.GONE);
                } else {
                    Mholder.cp_progress.setVisibility(View.GONE);
                    Mholder.tv_all_ed.setVisibility(View.VISIBLE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return arrayIsNull(array) ? 1 : array.length() + 1;
    }

    public boolean arrayIsNull(JSONArray array) {
        return array == null || array.length() == 0;
    }

    public void append(JSONArray array) {
        try {
            if (array == null || array.length() == 0) {
                this.notifyItemRangeChanged(this.array.length(), this.array.length() + 1);
                return;
            }
            for (int i = 0; i < array.length(); i++) {
                this.array.put(array.getJSONObject(i));
            }
            this.notifyItemRangeInserted(this.array.length() - array.length(), array.length());
            this.notifyItemRangeChanged(0, this.array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refresh(JSONArray array) {
        if (arrayIsNull(array)) {
            return;
        }
        int old_length = this.array.length();
        this.array = array;
        int new_length = this.array.length();
        this.notifyItemRangeRemoved(new_length - 1, old_length - new_length);
        this.notifyItemRangeChanged(0, new_length);

    }

    public void remove(int position) {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < this.array.length(); i++) {
                if (position == i) {
                    continue;
                }
                array.put(this.array.getJSONObject(i));
            }
            this.array = array;
            this.notifyItemRemoved(position);
            this.notifyItemRangeChanged(0, this.array.length());
        } catch (JSONException e) {
        }
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        TextView tv_name;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }

        CircleProgressBar cp_progress;
        TextView tv_all_ed;
    }
}
