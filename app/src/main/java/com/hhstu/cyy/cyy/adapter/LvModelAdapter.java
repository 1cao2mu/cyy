package com.hhstu.cyy.cyy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LvModelAdapter extends BaseAdapter {
    private JSONArray array = null;
    private Context mContext;
    OnItemChildViewClickListener onItemChildViewClickListener;

    public LvModelAdapter(JSONArray array, Context mContext) {
        this.mContext = mContext;
        this.array = array;
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public int getCount() {
        return array.length();
    }

    public Object getItem(int position) {
        try {
            return array.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        try {
            JSONObject object = array.getJSONObject(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_lv_model, null);
                viewHolder.tv_qu_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_qu_name.setText(object.getString("Name"));
            viewHolder.tv_qu_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemChildViewClickListener != null) {
                        onItemChildViewClickListener.onItemChildViewClickListener(v.getId(), position);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }
    public void refresh(JSONArray array) {
        this.array = array;
        this.notifyDataSetChanged();
    }

    public void append(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                this.array.put(array.getJSONObject(i));
            }
            this.notifyDataSetChanged();
        } catch (JSONException e) {
        }
    }
    /**
     * 移除item
     *
     * @param position
     */
    public void remove(int position) {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < this.array.length(); i++) {
                if (position == i) {
                    continue;
                }
                array.put(this.array.getJSONObject(i));
            }
            this.array=array;
            this.notifyDataSetChanged();
        } catch (JSONException e) {
        }
    }

    class ViewHolder {
        TextView tv_qu_name;
    }
}