package com.iai.ishoes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.iai.ishoes.R;

import java.util.Calendar;
import java.util.Date;

public class ReportFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mMyReportPrevious;
    private RelativeLayout mMyReportNext;
    private TextView mMyReportDate;
    private TextView mTvMyReport;

    private int add_day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        add_day = 0;
    }

    private void initView(View view) {
        mMyReportPrevious = view.findViewById(R.id.my_report_previous);
        mMyReportPrevious.setOnClickListener(this);
        mMyReportNext = view.findViewById(R.id.my_report_next);
        mMyReportNext.setOnClickListener(this);
        mMyReportDate = view.findViewById(R.id.my_report_date);
        mMyReportDate.setText(getDate(0).toString());
        mTvMyReport = view.findViewById(R.id.tv_my_report);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_report_previous:
                mMyReportDate.setText(getDate(-1).toString());

                break;
            case R.id.my_report_next:
                mMyReportDate.setText(getDate(1).toString());
                break;
        }
    }

    private String getDate(int n) {
        add_day += n;
        if (add_day == 0) {
            mMyReportNext.setEnabled(false);
        }else{
            mMyReportNext.setEnabled(true);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(c.DAY_OF_MONTH, add_day);
        String mDate = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日 ";
        return mDate;
    }
}