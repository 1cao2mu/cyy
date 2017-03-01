package com.hhstu.cyy.cyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.adapter.SelectPhotoAdapter;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SelectPhotoActivity extends BaseAppCompatActivity {

    RecyclerView rv_list;
    LinearLayoutManager linearLayoutManager;
    SelectPhotoAdapter adapter;
    private ArrayList<String> mResults = new ArrayList<>();
    private static final int REQUEST_CODE = 732;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_action).setOnClickListener(this);
        initView();
        initData();
    }


    private void initView() {
        ((TextView) findViewById(R.id.tv_action)).setText("选择");
        ((TextView) findViewById(R.id.tv_title)).setText("仿微信选择多张图片");
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        linearLayoutManager = new GridLayoutManager(getContext(), 4);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(linearLayoutManager);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_action:
                // start multiple photos selector
                intent = new Intent(getContext(), ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 10);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        try {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                    assert mResults != null;
                    // show results in textview
                    StringBuilder sb = new StringBuilder();
                    array = new JSONArray();
                    sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
                    for (String result : mResults) {
                        sb.append(result).append("\n");
                        JSONObject object = new JSONObject();
                        object.put("path", result);
                        array.put(object);
                    }
                    if (adapter != null) {
                        adapter.refresh(array);
                    } else {
                        adapter = new SelectPhotoAdapter(array, getContext());
                        adapter.setOnItemChildViewClickListener(SelectPhotoActivity.this);
                        rv_list.setAdapter(adapter);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
