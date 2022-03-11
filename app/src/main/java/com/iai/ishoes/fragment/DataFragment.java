package com.iai.ishoes.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iai.ishoes.R;
import com.iai.ishoes.adapter.MyRecycleViewAdapter;
import com.iai.ishoes.utils.EchartOptionUtil;
import com.iai.ishoes.view.EchartView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DataFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout mDatePrevious;
    private RelativeLayout mDateNext;
    private TextView mDataDate;
//    private BarChart mDataBarChart;
    private EchartView mDataBarChart;

    private TextView mDataL1;
    private TextView mDataL2;
    private TextView mDataL3;
    private TextView mDataL4;
    private TextView mDataL5;
    private TextView mDataL6;
    private TextView mDataL7;
    private TextView mDataL8;
    private TextView mDataR1;
    private TextView mDataR2;
    private TextView mDataR3;
    private TextView mDataR4;
    private TextView mDataR5;
    private TextView mDataR6;
    private TextView mDataR7;
    private TextView mDataR8;

    private int add_day;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        initData();
        initView(view);
//        setupChart();
        mDataBarChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Object[] y = new Object[]{
                        0, 1, 20, 4, 60, 10, 18, 30, 25, 40, 22, 1, 6, 8, 11
                };
                refreshLineChart(y);
            }
        });

        return view;
    }

    private void initView(View view) {
        mDatePrevious = view.findViewById(R.id.date_previous);
        mDatePrevious.setOnClickListener(this);
        mDateNext = view.findViewById(R.id.date_next);
        mDateNext.setOnClickListener(this);
        mDataDate = view.findViewById(R.id.data_date);
        mDataDate.setText(getDate(0).toString());
        mDataBarChart = view.findViewById(R.id.data_bar_chart);

        mDataL1 = view.findViewById(R.id.data_l1);
        mDataL2 = view.findViewById(R.id.data_l2);
        mDataL3 = view.findViewById(R.id.data_l3);
        mDataL4 = view.findViewById(R.id.data_l4);
        mDataL5 = view.findViewById(R.id.data_l5);
        mDataL6 = view.findViewById(R.id.data_l6);
        mDataL7 = view.findViewById(R.id.data_l7);
        mDataL8 = view.findViewById(R.id.data_l8);
        mDataR1 = view.findViewById(R.id.data_r1);
        mDataR2 = view.findViewById(R.id.data_r2);
        mDataR3 = view.findViewById(R.id.data_r3);
        mDataR4 = view.findViewById(R.id.data_r4);
        mDataR5 = view.findViewById(R.id.data_r5);
        mDataR6 = view.findViewById(R.id.data_r6);
        mDataR7 = view.findViewById(R.id.data_r7);
        mDataR8 = view.findViewById(R.id.data_r8);


//        mDataBarChart.setDrawBarShadow(false);
//        mDataBarChart.setDrawValueAboveBar(true);
//        mDataBarChart.getDescription().setEnabled(false);
//        // 如果60多个条目显示在图表,drawn没有值
//        mDataBarChart.setMaxVisibleValueCount(60);
//        // 扩展现在只能分别在x轴和y轴
//        mDataBarChart.setPinchZoom(false);
//        //是否显示表格颜色
//        mDataBarChart.setDrawGridBackground(false);
//
//        XAxis xAxis = mDataBarChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        // 只有1天的时间间隔
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(7);
//        xAxis.setAxisMaximum(24f);
//        xAxis.setAxisMinimum(0f);


//        YAxis leftAxis = mDataBarChart.getAxisLeft();
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        //这个替换setStartAtZero(true)
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(50f);
//
//        YAxis rightAxis = mDataBarChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f);
//        rightAxis.setAxisMaximum(50f);

        // 设置标示，就是那个一组y的value的
//        Legend l = mDataBarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        //样式
//        l.setForm(Legend.LegendForm.SQUARE);
//        //字体
//        l.setFormSize(9f);
//        //大小
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//
//        //模拟数据
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        yVals1.add(new BarEntry(0, 10));
//        yVals1.add(new BarEntry(5, 20));
//        yVals1.add(new BarEntry(16, 30));
//        yVals1.add(new BarEntry(18, 40));
//        yVals1.add(new BarEntry(20, 80));
//        yVals1.add(new BarEntry(22, 10));
//        yVals1.add(new BarEntry(25, 20));
//        yVals1.add(new BarEntry(28, 30));
//        yVals1.add(new BarEntry(30, 40));
//
//        setData(yVals1);
    }

    private void refreshLineChart(Object[] y){
        Object[] x = new Object[]{
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
        };

        mDataBarChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }

    private void initData() {
        add_day = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_previous:
                // 添加网络请求

                Object[] y = new Object[]{
                        20, 32, 1, 34, 90, 30, 20
                };
                refreshLineChart(y);

                mDataDate.setText(getDate(-1).toString());
                break;
            case R.id.date_next:
                mDataDate.setText(getDate(1).toString());
                break;
        }
    }

    private String getDate(int n) {
        add_day += n;
        if (add_day == 0) {
            mDateNext.setEnabled(false);
        }else{
            mDateNext.setEnabled(true);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(c.DAY_OF_MONTH, add_day);
        String mDate = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日 ";
        return mDate;
    }

    /**
     * @param f 电阻1-R/R0
     */
    public void setColors(List<Integer> f, boolean isLeft) {
//        calibrationTextColor();
        if(isLeft){
            setColor(f.get(0), isLeft, mDataL1);
            setColor(f.get(1), isLeft, mDataL2);
            setColor(f.get(2), isLeft, mDataL3);
            setColor(f.get(3), isLeft, mDataL4);
            setColor(f.get(4), isLeft, mDataL5);
            setColor(f.get(5), isLeft, mDataL6);
            setColor(f.get(6), isLeft, mDataL7);
            setColor(f.get(7), isLeft, mDataL8);
        }else{
            setColor(f.get(0), isLeft, mDataR1);
            setColor(f.get(1), isLeft, mDataR2);
            setColor(f.get(2), isLeft, mDataR3);
            setColor(f.get(3), isLeft, mDataR4);
            setColor(f.get(4), isLeft, mDataR5);
            setColor(f.get(5), isLeft, mDataR6);
            setColor(f.get(6), isLeft, mDataR7);
            setColor(f.get(7), isLeft, mDataR8);
        }
    }
    public void setColors(int[] f, boolean isLeft) {
//        calibrationTextColor();
        if(isLeft){
            setColor(f[0], isLeft, mDataL1);
            setColor(f[1], isLeft, mDataL2);
            setColor(f[2], isLeft, mDataL3);
            setColor(f[3], isLeft, mDataL4);
            setColor(f[4], isLeft, mDataL5);
            setColor(f[5], isLeft, mDataL6);
            setColor(f[6], isLeft, mDataL7);
            setColor(f[7], isLeft, mDataL8);
        }else{
            setColor(f[0], isLeft, mDataR1);
            setColor(f[1], isLeft, mDataR2);
            setColor(f[2], isLeft, mDataR3);
            setColor(f[3], isLeft, mDataR4);
            setColor(f[4], isLeft, mDataR5);
            setColor(f[5], isLeft, mDataR6);
            setColor(f[6], isLeft, mDataR7);
            setColor(f[7], isLeft, mDataR8);
        }
    }

    private void setColor(int level, boolean isLeft, TextView textView) {
        if (isLeft) {
            if (textView == mDataL1) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata1_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata1_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata1_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata1_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata1_4);
                }
            } else if (textView == mDataL2) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata2_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata2_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata2_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata2_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata2_4);
                }
            } else if (textView == mDataL3) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata3_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata3_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata3_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata3_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata3_4);
                }
            } else if (textView == mDataL4) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata4_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata4_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata4_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata4_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata4_4);
                }
            } else if (textView == mDataL5) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata5_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata5_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata5_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata5_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata5_4);
                }
            } else if (textView == mDataL6) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata6_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata6_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata6_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata6_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata6_4);
                }
            } else if (textView == mDataL7) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata7_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata7_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata7_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata7_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata7_4);
                }
            } else if (textView == mDataL8) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.ldata8_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.ldata8_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.ldata8_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.ldata8_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.ldata8_4);
                }
            }
        } else {
            if (textView == mDataR1) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata1_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata1_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata1_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata1_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata1_4);
                }
            } else if (textView == mDataR2) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata2_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata2_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata2_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata2_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata2_4);
                }
            } else if (textView == mDataR3) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata3_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata3_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata3_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata3_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata3_4);
                }
            } else if (textView == mDataR4) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata4_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata4_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata4_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata4_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata4_4);
                }
            } else if (textView == mDataR5) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata5_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata5_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata5_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata5_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata5_4);
                }
            } else if (textView == mDataR6) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata6_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata6_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata6_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata6_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata6_4);
                }
            } else if (textView == mDataR7) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata7_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata7_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata7_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata7_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata7_4);
                }
            } else if (textView == mDataR8) {
                if (level == -1) {
                    textView.setBackgroundResource(R.drawable.rdata8_null);
                } else if (level == 0) {
                    textView.setBackgroundResource(R.drawable.rdata8_1);
                } else if (level == 1) {
                    textView.setBackgroundResource(R.drawable.rdata8_2);
                } else if (level == 2) {
                    textView.setBackgroundResource(R.drawable.rdata8_3);
                }else if (level == 3) {
                    textView.setBackgroundResource(R.drawable.rdata8_4);
                }
            }
        }
    }





//    //设置数据
//    private void setData(ArrayList yVals1) {
//        BarDataSet set1;
//        if (mDataBarChart.getData() != null &&
//                mDataBarChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) mDataBarChart.getData().getDataSetByIndex(0);
//            set1.setValues(yVals1);
//            mDataBarChart.getData().notifyDataChanged();
//            mDataBarChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "2017年工资涨幅");
////            //设置有四种颜色
//            set1.setColors(ColorTemplate.MATERIAL_COLORS);
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setBarWidth(0.9f);
//            //设置数据
//            mDataBarChart.setData(data);
//        }
//    }
}
