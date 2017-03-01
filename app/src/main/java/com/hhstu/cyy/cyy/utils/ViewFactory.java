package com.hhstu.cyy.cyy.utils;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.application.MineApplication;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		NetworkImageView imageView = new NetworkImageView(context);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setDefaultImageResId(R.mipmap.ic_launcher);
		imageView.setErrorImageResId(R.mipmap.ic_launcher);
		imageView.setImageUrl(url, MineApplication.getInstance().getImageLoader());
		return imageView;
	}
}
