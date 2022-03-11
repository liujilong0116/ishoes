package com.iai.ishoes.fragment;

import static com.iai.ishoes.activity.MainActivity.ACTIVE_DISCONNECT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iai.ishoes.R;
import com.iai.ishoes.activity.MainActivity;

public class MySettingBindMacFragment extends Fragment implements View.OnClickListener {

    private TextView mTvMacAddress;
    private RadioButton mBtnUnbind;
    private RadioButton mBtnBind;
    private SharedPreferences sp;
    private RelativeLayout mBtnBindMac;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_fragment_bindblueth, container, false);
        initData();
        initView(view);
        back();
        return view;
    }

    private void back() {
        mBtnBindMac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MySettingFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MySettingBindMacFragment.this).commit();
                }
            }
        });
    }

    private void initData() {
        sp = getActivity().getSharedPreferences("cache", Context.MODE_PRIVATE);
    }

    private void initView(View view) {
        mTvMacAddress = (TextView) view.findViewById(R.id.tv_mac_address);
        String bind = sp.getString("bind", "");
        if (!"" .equals(bind)) {
            String bind1 = processString(bind);
            mTvMacAddress.setText(bind1);
        } else {
            mTvMacAddress.setText("未绑定任何蓝牙设备");
        }
        mBtnUnbind = (RadioButton) view.findViewById(R.id.unbind);
        mBtnUnbind.setOnClickListener(this);
        mBtnBind = (RadioButton) view.findViewById(R.id.bind);
        mBtnBind.setOnClickListener(this);
        mBtnBindMac = view.findViewById(R.id.btn_bind_mac);
        mBtnBindMac.setOnClickListener(this);
    }

    private String processString(String result) {
        String name = null;
        final String[] s = result.trim().split(",");
        if (s.length == 3) {
            String[] s1 = s[0].split("=");
            String[] s2 = s[1].split("=");
            name = s1[0] + " | " + s2[0];
        }
        return name;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unbind:
                MainActivity mainActivity = (MainActivity) getActivity();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("bind", null);
                editor.apply();
                mTvMacAddress.setText("未绑定任何蓝牙设备");
                mainActivity.handler.sendEmptyMessage(ACTIVE_DISCONNECT);
                Fragment fragment = new MySettingFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MySettingBindMacFragment.this).commit();
                }
//                if (mainActivity != null) {
//                    mainActivity.mIbSearchConnection.setEnabled(true);
//                }
                break;
            case R.id.bind:
                String result1 = sp.getString("result", "");
                String result2 = processString(result1);
                mTvMacAddress.setText(result2);
                SharedPreferences.Editor editor2 = sp.edit();
                editor2.putString("bind", result1);
                editor2.apply();
                break;
        }
    }
}
