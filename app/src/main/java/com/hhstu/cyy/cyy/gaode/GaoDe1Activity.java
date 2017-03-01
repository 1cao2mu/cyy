package com.hhstu.cyy.cyy.gaode;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.gaode.location.Battery_Saving_Activity;
import com.hhstu.cyy.cyy.gaode.location.view.*;

public class GaoDe1Activity extends ListActivity {
    private static final DemoDetails[] demos = {
            new DemoDetails(R.string.battery_saving,
                    R.string.battery_saving_dec, Battery_Saving_Activity.class)
//            , new DemoDetails(R.string.device_sensors,
//                    R.string.device_sensors_dec, Device_Sensors_Activity.class)
//            , new DemoDetails(R.string.hight_accuracy,
//                    R.string.hight_accuracy_dec, Hight_Accuracy_Activity.class)
//            , new DemoDetails(R.string.lastLocation, R.string.lastLocation_dec,
//                    LastLocation_Activity.class)
//            , new DemoDetails(R.string.geoFenceAlert, R.string.geoFenceAlert_dec,
//                    GeoFence_Activity.class)
//            , new DemoDetails(R.string.assistantLocation,
//                    R.string.assistantLocation_dec,
//                    Assistant_Location_Activity.class)
//            , new DemoDetails(R.string.alarmCPU, R.string.alarmCPU_dec,
//                    Alarm_Location_Activity.class)
//            , new DemoDetails(R.string.errorCode, R.string.errorCode_dec,
//                    ErrorCode_Activity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gao_de1);
        setTitle(R.string.title_main);
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        startActivity(new Intent(this.getApplicationContext(), demo.activityClass));
    }

    private static class DemoDetails {
        private final int titleId;
        private final int descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.view_feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId);
            featureView.setDescriptionId(demo.descriptionId);
            return featureView;
        }
    }
}
