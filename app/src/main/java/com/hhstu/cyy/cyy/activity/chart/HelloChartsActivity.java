package com.hhstu.cyy.cyy.activity.chart;

import android.os.Bundle;
import android.widget.Toast;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class HelloChartsActivity extends BaseAppCompatActivity {

    private LineChartView lcv;

    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 10;
    private int numberOfPoints = 30;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = true;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_charts);
        initView();
    }

    private void initView() {
        lcv = (LineChartView) findViewById(R.id.lcv);
        lcv.setOnValueTouchListener(new ValueTouchListener());

        // Generate some random values.
        generateValues();

        generateData();

        // Disable viewport recalculations, see toggleCubic() method for more info.
        lcv.setViewportCalculationEnabled(false);

        resetViewport();
    }
    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {

        }

    }
    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }
    }

    private void generateData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        lcv.setLineChartData(data);
    }
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(lcv.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints - 1;
        lcv.setMaximumViewport(v);
        lcv.setCurrentViewport(v);
    }
}
