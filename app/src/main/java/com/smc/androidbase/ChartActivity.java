package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/23
 * @description
 */

public class ChartActivity extends Activity {

    @BindView(R.id.linecharview)
    LineChartView mLinecharview;

    private List<PointValue> mPointValues1;
    private List<PointValue> mPointValues2;
    private LineChartData mLineChartData;
    private int mIndex = 6;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChartActivity.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);


        List<Line> lineList = new ArrayList<>();
        lineList.add(buildLine1());
        lineList.add(buildLine2());

        mLineChartData = new LineChartData();
        mLineChartData.setLines(lineList);
        mLineChartData.setAxisXBottom(buildAxisX(0.0f));
        mLineChartData.setAxisYLeft(buildAxisY());
        mLinecharview.setLineChartData(mLineChartData);

        Viewport currentPort = new Viewport(0.0f, 100f, 10.0f, 0.0f);
        Viewport MaxPort = new Viewport(0.0f, 100f, 10.0f, 0.0f);
        mLinecharview.setMaximumViewport(MaxPort);
        mLinecharview.setCurrentViewport(currentPort);
        addPoint();
    }

    private Axis buildAxisX(float from) {
        Axis axisX = Axis.generateAxisFromRange(from, from + 10f, 1f);
        axisX.setTextColor(Color.GRAY);
        axisX.setTextSize(8);
        axisX.setName("时间(s)");
        return axisX;
    }

    private Axis buildAxisY() {
        Axis axisX = Axis.generateAxisFromRange(0f, 100f, 10f);
        axisX.setTextColor(Color.GRAY);
        axisX.setTextSize(8);
        axisX.setName("参数值(%)");
        return axisX;
    }


    private void addPoint() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int value1 = (int) (Math.random() * 100);
                    int value2 = (int) (Math.random() * 100);
                    mIndex++;
                    PointValue pointValue1 = new PointValue(mIndex, value1);
                    mPointValues1.add(pointValue1);

                    if (mPointValues1.size() > 10) {
                        mPointValues1.remove(0);
                    }
                    PointValue pointValue2 = new PointValue(mIndex, value2);
                    mPointValues2.add(pointValue2);
                    if (mPointValues2.size() > 10) {
                        mPointValues2.remove(0);
                    }
                    if (value1 < 40) {
                        Log.i("chart", "恭喜在第" + mIndex + "次中签");
                        mIndex = 1;
                    }
                    mLineChartData.setAxisXBottom(buildAxisX(mPointValues1.get(0).getX()));
                    mLinecharview.setLineChartData(mLineChartData);
                    Viewport currentPort = new Viewport(mPointValues1.get(0).getX() + 0.0f, 100f, mPointValues1.get(0).getX() + 10.0f, 0.0f);
//                    Viewport MaxPort = new Viewport(0.0f, 100f, 10.0f, 0.0f);
                    mLinecharview.setMaximumViewport(currentPort);
                    mLinecharview.setCurrentViewport(currentPort);

//                    mMainHandler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private Line buildLine1() {
        mPointValues1 = new ArrayList<>();
        PointValue pointValue1 = new PointValue(0, 10);
        PointValue pointValue2 = new PointValue(1, 40);
        PointValue pointValue3 = new PointValue(2, 13);
        PointValue pointValue4 = new PointValue(3, 98);
        PointValue pointValue5 = new PointValue(4, 12);
        PointValue pointValue6 = new PointValue(5, 42);
        PointValue pointValue7 = new PointValue(6, 86);
        mPointValues1.add(pointValue1);
        mPointValues1.add(pointValue2);
        mPointValues1.add(pointValue3);
        mPointValues1.add(pointValue4);
        mPointValues1.add(pointValue5);
        mPointValues1.add(pointValue6);
        mPointValues1.add(pointValue7);

        Line line = new Line();
        line.setValues(mPointValues1);
        line.setColor(Color.BLUE).setCubic(true);
        line.setHasLabels(true);
        return line;
    }

    private Line buildLine2() {
        mPointValues2 = new ArrayList<>();
        PointValue pointValue1 = new PointValue(0, 22);
        PointValue pointValue2 = new PointValue(1, 12);
        PointValue pointValue3 = new PointValue(2, 31);
        PointValue pointValue4 = new PointValue(3, 12);
        PointValue pointValue5 = new PointValue(4, 67);
        PointValue pointValue6 = new PointValue(5, 12);
        PointValue pointValue7 = new PointValue(6, 35);
        mPointValues2.add(pointValue1);
        mPointValues2.add(pointValue2);
        mPointValues2.add(pointValue3);
        mPointValues2.add(pointValue4);
        mPointValues2.add(pointValue5);
        mPointValues2.add(pointValue6);
        mPointValues2.add(pointValue7);

        Line line = new Line();
        line.setValues(mPointValues2);
        line.setColor(Color.RED).setCubic(true);
        return line;
    }
}
