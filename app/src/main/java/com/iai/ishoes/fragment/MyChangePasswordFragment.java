package com.iai.ishoes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iai.ishoes.R;

public class MyChangePasswordFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout mBtnChangePasswordBack;
    private EditText mEtOriginPassword;
    private EditText mEtNewPassword;
    private EditText mEtConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initData();
        initView(view);
        back();
        return view;
    }

    private void initView(View view) {
//        mTvMacAddress = (TextView) view.findViewById(R.id.tv_mac_address);
//        String bind = sp.getString("bind", "");
//        if (!"" .equals(bind)) {
//            String bind1 = processString(bind);
//            mTvMacAddress.setText(bind1);
//        } else {
//            mTvMacAddress.setText("未绑定任何蓝牙设备");
//        }
//        mBtnUnbind = (RadioButton) view.findViewById(R.id.unbind);
//        mBtnUnbind.setOnClickListener(this);
//        mBtnBind = (RadioButton) view.findViewById(R.id.bind);
//        mBtnBind.setOnClickListener(this);
        mEtOriginPassword = view.findViewById(R.id.et_origin_password);
        mEtNewPassword = view.findViewById(R.id.et_new_password);
        mEtConfirmPassword = view.findViewById(R.id.et_confirm_password);
        mBtnChangePasswordBack = view.findViewById(R.id.btn_change_password_back);
        mBtnChangePasswordBack.setOnClickListener(this);
    }

    private void initData() {

    }

    private void back() {
        mBtnChangePasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MySettingFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MyChangePasswordFragment.this).commit();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_password_back:
                break;
        }
    }
}
