package com.hhstu.cyy.cyy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/6/17 .
 */
public class ChartView extends View {
    public int XPoint = 40;    //原点的X坐标
    public int YPoint = 260;     //原点的Y坐标
    public int XScale = 55;     //X的刻度长度
    public int YScale = 40;     //Y的刻度长度
    public int XLength = 1000;        //X轴的长度
    public int YLength = 1000;        //Y轴的长度
    public String[] XLabel;    //X的刻度
    public String[] YLabel;    //Y的刻度
    public String[] Data;      //数据
    public String Title = "";    //显示的标题

    public ChartView(Context context) {
        super(context);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetInfo(String[] XLabels, String[] YLabels, String[] AllData, String strTitle) {
        XLabel = XLabels;
        YLabel = YLabels;
        Data = AllData;
        Title = strTitle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//重写onDraw方法

        //canvas.drawColor(Color.WHITE);//设置背景颜色
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.BLUE);//颜色
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);//去锯齿
        paint1.setColor(Color.DKGRAY);
        paint.setTextSize(12);  //设置轴文字大小
        //设置Y轴
        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);   //轴线
        for (int i = 0; i * YScale < YLength; i++) {
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i * YScale, paint);  //刻度
            try {
                canvas.drawText(YLabel[i], XPoint - 22, YPoint - i * YScale + 5, paint);  //文字
            } catch (Exception e) {
            }
        }
        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength + 6, paint);  //箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength + 6, paint);
        //设置X轴
        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint);   //轴线
        for (int i = 0; i * XScale < XLength; i++) {
            canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale, YPoint - 5, paint);  //刻度
            try {
                canvas.drawText(XLabel[i], XPoint + i * XScale - 10, YPoint + 20, paint);  //文字
                //数据值
                if (i > 0 && YCoord(Data[i - 1]) != -999 && YCoord(Data[i]) != -999)  //保证有效数据
                    canvas.drawLine(XPoint + (i - 1) * XScale, YCoord(Data[i - 1]), XPoint + i * XScale, YCoord(Data[i]), paint);
                canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 2, paint);
            } catch (Exception e) {
            }
        }
        canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6, YPoint - 3, paint);    //箭头
        canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6, YPoint + 3, paint);
        paint.setTextSize(16);
        canvas.drawText(Title, 150, 50, paint);
    }

    private int YCoord(String y0)  //计算绘制时的Y坐标，无数据时返回-999
    {
        int y;
        try {
            y = Integer.parseInt(y0);
        } catch (Exception e) {
            return -999;    //出错则返回-999
        }
        try {
            return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
        } catch (Exception e) {
        }
        return y;
    }
}


//        调用方法：
//        ChartView myView=new ChartView(this);
//        myView.SetInfo(
//        new String[]{"7-11","7-12","7-13","7-14","7-15","7-16","7-17"},   //X轴刻度
//        new String[]{"","50","100","150","200","250"},   //Y轴刻度
//        new int[]{15,23,10,36,45,40,12},  //数据
//        "图标的标题"
//        );
