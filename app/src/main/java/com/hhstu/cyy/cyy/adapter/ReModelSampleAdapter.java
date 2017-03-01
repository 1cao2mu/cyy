package com.hhstu.cyy.cyy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/13.
 */
public class ReModelSampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    JSONArray array;
    Context context;
    OnItemChildViewClickListener onItemChildViewClickListener;



    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public ReModelSampleAdapter(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View item = LayoutInflater.from(context).inflate(R.layout.item_re_model, null);
            ItemViewHolder viewHolder = new ItemViewHolder(item);
            viewHolder.tv_name = (TextView) item.findViewById(R.id.tv_name);
            return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        try {

                ItemViewHolder Mholder = (ItemViewHolder) holder;
                JSONObject object = array.getJSONObject(position);
                Mholder.tv_name.setText(object.getString("Name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return array.length();
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

}
