package com.hhstu.cyy.cyy.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
public class Data {
    public static JSONArray getData(int page) {
        JSONArray array = new JSONArray();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        try {
//            if (page==1){
//                return  array;
//            }
            for (int i = (page - 1) * 20; i < page * 20; i++) {
                JSONObject object = new JSONObject();
                object.put("url", "http://finance.gucheng.com/UploadFiles_7830/201504/2015040209572762.jpg");
                object.put("Content", i + ".咱们的程序功能太多了啊");
                object.put("Count", i);
                object.put("Tel", "15093427944");
                object.put("Name", "编号" + i);
                object.put("Thing", "失物" + i);
                object.put("Address", i + "楼");
                object.put("AddTime", date);
                if (i % 2 == 0) {
                    object.put("ReContent", "你的问题问的真好啊");
                    object.put("ReTime", date);
                    object.put("image", "http://finance.gucheng.com/UploadFiles_7830/201504/2015040209572762.jpg");
                }
                array.put(object);
                if ( i == 33) {
                    return array;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
}
