package com.hhstu.cyy.cyy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.application.MineApplication;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class LvJsouplAdapter extends BaseAdapter {
    private List<String> data;
    private Context mContext;

    public LvJsouplAdapter(List<String> data, Context mContext) {
        this.mContext = mContext;
        this.data = data;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        String s = data.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_lv_jsoup, null);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
            viewHolder.iv_content = (NetworkImageView) view.findViewById(R.id.iv_content);
            viewHolder.iv_content.setDefaultImageResId(R.mipmap.tab_red);
            viewHolder.iv_content.setErrorImageResId(R.mipmap.tab_red);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (s.startsWith("http")){
            viewHolder.tv_content.setVisibility(View.GONE);
            viewHolder.iv_content.setVisibility(View.VISIBLE);
            viewHolder.iv_content.setImageUrl(s, MineApplication.getInstance().getImageLoader());
        }else {
            viewHolder.tv_content.setVisibility(View.VISIBLE);
            viewHolder.tv_content.setText(s);
            viewHolder.iv_content.setVisibility(View.GONE);
        }
        viewHolder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv_content;
        NetworkImageView iv_content;
    }
}