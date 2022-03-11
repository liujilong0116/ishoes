
package com.iai.ishoes.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iai.ishoes.R;
import com.iai.ishoes.adapter.MyRecycleViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class MyFragment extends Fragment {

    public RecyclerView mRecV;
    private TextView appVersionTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }


    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("我的账号");
        data.add("指导意见");
        data.add("设置");
        return data;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView(View view) {
        mRecV = view.findViewById(R.id.recv);
        appVersionTv = view.findViewById(R.id.appVersionTv);
        try {
            PackageInfo pi =view.getContext().getPackageManager().getPackageInfo(view.getContext().getPackageName(),0);
            appVersionTv.setText("当前版本 "+pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mRecV.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(getContext(), getData());
        mRecV.setAdapter(adapter);
        mRecV.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.setOnItemClickListener(new MyRecycleViewAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object, int position) {
                switch (position) {
                    case 0://account
                        Fragment fragment_account = new MyAccountFragment();
                        if (!fragment_account.isAdded()) {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment_account).commit();
                        } else {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().show(fragment_account).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().hide(MyFragment.this).commit();
                        }
                        break;
                    case 1://instruction
                        Fragment fragment_instruction = new MyInstructionFragment();
                        if (!fragment_instruction.isAdded()) {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment_instruction).commit();
                        } else {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().show(fragment_instruction).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().hide(MyFragment.this).commit();
                        }
                        break;
                    case 2://setting
                        Fragment fragment_setting = new MySettingFragment();
                        if (!fragment_setting.isAdded()) {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment_setting).commit();
                        } else {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().show(fragment_setting).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().hide(MyFragment.this).commit();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
