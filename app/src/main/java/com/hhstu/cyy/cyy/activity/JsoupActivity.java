package com.hhstu.cyy.cyy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.adapter.LvJsouplAdapter;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupActivity extends BaseAppCompatActivity {
    private ListView rv_list;
    Document doc = null;
    private StringBuilder sb;
    private List<String> data = new ArrayList<>();
    private LvJsouplAdapter lvJsouplAdapter;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            dismissSwipeRefreshLayout(null, dialog);
            sb = new StringBuilder();
            sb.append(doc.getElementById("activity-name").text()).append("\n");
            sb.append(doc.getElementById("post-date").text()).append("\n");
            sb.append(doc.getElementById("post-user").text()).append("\n");

            Element e = doc.getElementById("js_content");
            Elements es = e.child(0).children();
            int esi = es.size();
            sb.append(esi).append("\n");
            for (int i = 0; i < es.size(); i++) {
                Element ee = es.get(i);
                getSb(ee, i);
//                if ()
//                sb.append(ee.toString()).append(ee.childNodeSize()).append(i).append("哈哈\n");
//                sb.append(ee.attributes().toString()).append(i).append("呵呵\n");
            }
//            tv_content.setText(sb.toString());
            String[] ss = sb.toString().split("\n");
            for (int i = 0; i < ss.length; i++) {
                data.add(ss[i]);
            }
            lvJsouplAdapter = new LvJsouplAdapter(data, getContext());
            rv_list.setAdapter(lvJsouplAdapter);
//            Log.e("handleMessage", sb.toString());
            return false;
        }
    });

    public void getSb(Element element, int i) {
        Log.e("getSb", element.toString() + element.childNodeSize());
        if (element.tag().equals(Tag.valueOf("p")) || element.tag().equals(Tag.valueOf("strong"))) {
            sb.append(element.text()).append("\n");
        } else if (element.tag().equals(Tag.valueOf("img"))) {
            sb.append(element.attr("data-src")).append("\n");
        } else if (element.tag().equals(Tag.valueOf("br"))) {

        } else if (element.childNodeSize() == 0 && element.tag().equals(Tag.valueOf("section"))) {

        } else if (element.childNodeSize() == 0) {
            sb.append(element.toString()).append(element.tag().toString()).append(i).append("哈哈\n");
        } else {
            for (int i1 = 0; i1 < element.children().size(); i1++) {
                Element eee = element.child(i1);
                getSb(eee, i);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup);
//        tv_content = (TextView) findViewById(R.id.tv_content);
        rv_list = (ListView) findViewById(R.id.rv_list);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
//                    doc = Jsoup.connect("https://qy.weixin.qq.com/cgi-bin/wap_getnewsmsg?action=get&__biz=MzI1NjQ3MzA0NA==&mixuin=MjE5NDc2MzczNTY3OTE0MjQzNg==&mid=10001055&idx=1&sn=473d6223958dcbacc79cb73ec92cfdbf&scene=2&from=timeline&isappinstalled=0").get();
                    doc = Jsoup.connect("http://mp.weixin.qq.com/s/AkhFbmv2C-Q1Af7tfV4btg").get();
                    String title = doc.title();
                    handler.sendEmptyMessage(0);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        showSwipeRefreshLayout(null, dialog);
    }
}
