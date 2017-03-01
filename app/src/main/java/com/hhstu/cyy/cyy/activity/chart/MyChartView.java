package com.hhstu.cyy.cyy.activity.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.hhstu.cyy.cyy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CYY
 * on 2017/1/12.
 */

public class MyChartView extends SeekBar {
    //tag
    private String TAG = "MyChartView";
    //View 的宽和高  px
    private int mWidth, mHeight;
    //x轴的集合
    private List<String> xList = new ArrayList<>();
    //y轴的集合
    private List<Integer> yList = new ArrayList<>();
    //点的集合
    private HashMap<String, Integer> dotMap = new HashMap<>();
    //Y轴字体的大小
    private float mXYFontSize = 38;
    //没有数据的时候的内容
    private String mNoDataMsg = "no data";
    //线条颜色
    private int mLineColor = Color.GREEN;
    //线条的宽度
    private float mStrokeWidth = 4.0f;
    //点的半径
    private float mPointRadius = 7;
    //X轴坐标点  string(x坐标名,日期),Integer(坐标x值)
    private HashMap<String, Integer> xdotMap = new HashMap<>();
    //y轴坐标点  Integer(坐标y值)
    private List<Float> ydotlist = new ArrayList<>();

    private Context context;
    private int cusor_y;

    public MyChartView(Context context) {
        super(context);
        this.context = context;
        initList();
        initMap();
    }

    public MyChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initList();
        initMap();
    }

    public MyChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initList();
        initMap();
    }

    public void setxList(List<String> xList) {
        this.xList = xList;
    }

    public void setyList(List<Integer> yList) {
        this.yList = yList;
    }

    public void setDotMap(HashMap<String, Integer> dotMap) {
        this.dotMap = dotMap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("width must be EXACTLY,you should set like android:width=\"200dp\"");
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("height must be EXACTLY,you should set like android:height=\"200dp\"");
        }
//        Log.e(TAG, "mWidth: " + mWidth);
//        Log.e(TAG, "mHeight: " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (xList.size() == 0 || yList.size() == 0) {
            //为空时初始化
            throw new IllegalArgumentException("X or Y items is null");
        }
        //画坐标线的轴
        Paint axisPaint = new Paint();
        axisPaint.setTextSize(mXYFontSize);
        axisPaint.setColor(Color.BLUE);

        if (dotMap.size() == 0) {
            int textLength = (int) axisPaint.measureText(mNoDataMsg);
            canvas.drawText(mNoDataMsg, mWidth / 2 - textLength / 2, mHeight / 2, axisPaint);
        } else {
            //计算Y轴 每个刻度的间距
            int yInterval = mHeight / (yList.size() + 2);
            //测量Y轴文字的高度 用来画第一个数
            int pading = 60;
            ydotlist.clear();
            for (int i = 0; i < yList.size(); i++) {
                //写y轴文字,暂时的,正式去除
                canvas.drawText(String.valueOf(yList.get(yList.size() - 1 - i)) + (i + 0.5f) * yInterval, 0, (i + 0.5f) * yInterval, axisPaint);
                //画五条线,平行x轴,垂直于y轴
                canvas.drawLine(0 + pading, (i + 0.5f) * yInterval, mWidth - pading, (i + 0.5f) * yInterval, axisPaint);
                ydotlist.add((i + 0.5f) * yInterval);
            }
            ydotlist.add((5 + 0.5f) * yInterval);
            //画x轴,暂时的 正式需要去除
            axisPaint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(0) + (5 + 0.5f) * yInterval, 0, (5 + 0.5f) * yInterval, axisPaint);
            canvas.drawLine(0 + pading, (5 + 0.5f) * yInterval, mWidth - pading, (5 + 0.5f) * yInterval, axisPaint);
            //游标轴
            canvas.drawLine(0 + pading, (6 + 0.2f) * yInterval, mWidth - pading, (6 + 0.2f) * yInterval, axisPaint);


            //画X轴
            float x_y = (5 + 0.5f) * yInterval;//x轴的y坐标位置
            //计算Y轴开始的原点坐标
            int xInterval = (mWidth - (pading * 2)) / (xList.size() - 1);
            axisPaint.setColor(Color.YELLOW);
            axisPaint.setTextAlign(Paint.Align.CENTER);
            xdotMap.clear();
            for (int i = 0; i < xList.size(); i++) {
                canvas.drawText(String.valueOf(xList.get(i)), i * xInterval + pading, x_y + mXYFontSize, axisPaint);
                canvas.drawLine(i * xInterval + pading, 0, i * xInterval + pading, mHeight, axisPaint);
                xdotMap.put(xList.get(i), i * xInterval + pading);
// xPoints[i] = (int) (i * xInterval + xItemX + axisPaint.measureText(String.valueOf(xList.get(1))) / 2 + xOffset + 10);
// Log.e("wing", xPoints[i] + "");
            }
            //画阴影
            Path path = new Path();
            Paint pathPaint = new Paint();
            for (int i = 0; i < xList.size(); i++) {
                if (i == 0) {
                    path.moveTo(xdotMap.get(xList.get(i)), getYPoint(dotMap.get(xList.get(i)), yInterval));
                } else {
                    path.lineTo(xdotMap.get(xList.get(i)), getYPoint(dotMap.get(xList.get(i)), yInterval));
                }
            }
            path.lineTo((xList.size() - 1) * xInterval + pading, x_y);
            path.lineTo(pading, x_y);
            path.close();
//            path.lineTo(xdotMap.get(xList.get(0)), getYPoint(dotMap.get(xList.get(0)), yInterval));
            pathPaint.setColor(Color.BLACK);
            pathPaint.setStyle(Paint.Style.FILL);// 设置为空心
            pathPaint.setStrokeWidth((float) 3.0);
            canvas.drawPath(path, pathPaint);

            //画点
            Paint pointPaint = new Paint();
            pointPaint.setColor(mLineColor);
            pointPaint.setStrokeWidth(3);
            Paint linePaint = new Paint();
            linePaint.setColor(mLineColor);
            linePaint.setAntiAlias(true);
            linePaint.setStrokeWidth(mStrokeWidth);//设置线条宽度
            for (int i = 0; i < xList.size(); i++) {
                if (i > 0) {
                    //画线
                    canvas.drawLine(xdotMap.get(xList.get(i - 1)),
                            getYPoint(dotMap.get(xList.get(i - 1)), yInterval),
                            xdotMap.get(xList.get(i)),
                            getYPoint(dotMap.get(xList.get(i)), yInterval),
                            linePaint);
                }
            }
            for (int i = 0; i < xList.size(); i++) {
                //画点
                pointPaint.setStyle(Paint.Style.STROKE);
                pointPaint.setColor(mLineColor);
                canvas.drawCircle(xdotMap.get(xList.get(i)), getYPoint(dotMap.get(xList.get(i)), yInterval), 7, pointPaint);
                pointPaint.setColor(ActivityCompat.getColor(context, R.color.text_red));
                pointPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(xdotMap.get(xList.get(i)), getYPoint(dotMap.get(xList.get(i)), yInterval), 5, pointPaint);

            }


            int id;
            if (ischoose) {
                id = R.mipmap.slider_pressed;
                axisPaint.setStrokeWidth(3.0f);
            } else {
                id = R.mipmap.slider_normal;
                axisPaint.setStrokeWidth(1.5f);
            }
            axisPaint.setColor(ActivityCompat.getColor(context, R.color.bg_purple));
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            if (ex == -1) {
                cusor_y = (int) ((6 + 0.2) * yInterval);
                Rect mDestRect = new Rect(pading - bitmap.getWidth() / 4, cusor_y - bitmap.getHeight() / 4
                        , pading + bitmap.getWidth() / 4, cusor_y + bitmap.getHeight() / 4);
                canvas.drawLine(pading, 0, pading, mHeight, axisPaint);
                canvas.drawBitmap(bitmap, null, mDestRect, axisPaint);
            } else {
                if (isActionUp) {
                    for (int i = 0; i < xList.size(); i++) {
                        int hi = xdotMap.get(xList.get(i));


                        if (Math.abs(hi - ex) < xInterval) {
                            if (i != xList.size() - 1
                                    && Math.abs(hi - ex) > Math.abs(xdotMap.get(xList.get(i + 1)) - ex)) {
                                ex = xdotMap.get(xList.get(i + 1));
                            } else {
                                ex = hi;
                            }
                            break;

                        }

                    }
                }

                Rect mDestRect = new Rect(ex - bitmap.getWidth() / 4, cusor_y - bitmap.getHeight() / 4
                        , ex + bitmap.getWidth() / 4, cusor_y + bitmap.getHeight() / 4);
                canvas.drawLine(ex, 0, ex, mHeight, axisPaint);
                canvas.drawBitmap(bitmap, null, mDestRect, axisPaint);
            }


        }
    }

    private float getYPoint(Integer integer, int yInterval) {
        int oney = yList.get(1) - yList.get(0);
        float x_y = ydotlist.get(ydotlist.size() - 1);
        float h1 = ((float) yInterval / oney * integer);
//        Log.e(TAG, "oney: " + oney);
//        Log.e(TAG, "x_y: " + x_y);
//        Log.e(TAG, "h1: " + h1);
//        Log.e(TAG, "x_y - h1: " + (x_y - h1));
        return x_y - h1;
    }


    private int ex = -1;
    private boolean ischoose = false;
    private boolean isActionUp = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                ischoose = false;
                isActionUp = true;
                ex = (int) event.getX();
                invalidate();
                break;
            default:
                ischoose = true;
                isActionUp = false;
                ex = (int) event.getX();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setActionUp(int ex) {
        ischoose = false;
        isActionUp = true;
//        this.ex = ex;
        invalidate();
    }


    private void initMap() {
        dotMap.clear();
        for (int i = 0; i < xList.size(); i++) {
            dotMap.put(xList.get(i), i > 5 ? i - 5 : i);
        }
    }

    private void initList() {
        xList.clear();
        xList.add("01/01");
        xList.add("01/02");
        xList.add("01/03");
        xList.add("01/04");
        xList.add("01/05");
        xList.add("01/06");
        xList.add("01/07");
        yList.clear();
        yList.add(1);
        yList.add(2);
        yList.add(3);
        yList.add(4);
        yList.add(5);
    }

    // 修改笔的颜色
    private Shader getShadeColor() {
        Shader mShader = new LinearGradient(300, 50, 300, 400,
                new int[]{Color.parseColor("#55FF7A00"), Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
        // 新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
        return mShader;
    }

}
