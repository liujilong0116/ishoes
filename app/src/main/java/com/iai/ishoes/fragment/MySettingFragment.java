/**
 *
 */
package com.iai.ishoes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iai.ishoes.R;
import com.iai.ishoes.adapter.MyRecycleViewAdapter;

import java.util.ArrayList;


public class MySettingFragment extends Fragment {

    private RelativeLayout mBtnMySettingBack;
    private RecyclerView mRecyclerView;
    private PopupWindow popupWindow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mysetting, container, false);
        view.setClickable(true);
        initView(view);
        back();
        return view;
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("蓝牙绑定");
        data.add("密码修改");
        data.add("退出");
        return data;
    }

    /**
     * 返回“我的”界面按钮
     */
    private void back() {
        mBtnMySettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MySettingFragment.this).commit();
                }
            }
        });
    }

    private void initView(View view) {
        mBtnMySettingBack = view.findViewById(R.id.btn_mysetting_back);
        mRecyclerView = view.findViewById(R.id.recv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(adapter);
        // 各个adapter绑定点击事件
        adapter.setOnItemClickListener(new MyRecycleViewAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object, int position) {
                switch (position) {
                    case 0://unbind
                        Fragment fragment_bind = new MySettingBindMacFragment();
                        if (!fragment_bind.isAdded()) {
                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment_bind).commit();
                        } else {
                            getActivity().getSupportFragmentManager().beginTransaction().show(fragment_bind).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().remove(MySettingFragment.this).commit();
                        }
                        break;
                    case 1://password
                        Fragment fragment_password = new MyChangePasswordFragment();
                        if (!fragment_password.isAdded()) {
                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment_password).commit();
                        } else {
                            getActivity().getSupportFragmentManager().beginTransaction().show(fragment_password).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().remove(MySettingFragment.this).commit();
                        }
                        break;
                    case 2://popup
                        Fragment fragment_popup = new MyBackPopupFragment();
                        if (!fragment_popup.isAdded()) {
                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment_popup).commit();
                        } else {
                            getActivity().getSupportFragmentManager().beginTransaction().show(fragment_popup).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().remove(MySettingFragment.this).commit();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
