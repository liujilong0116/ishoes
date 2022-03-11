package com.iai.ishoes.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iai.ishoes.R;
import com.iai.ishoes.activity.MainActivity;

public class MyBackPopupFragment extends Fragment implements View.OnClickListener{
    private LinearLayout mSignOut;
    private LinearLayout mCloseApp;
    private LinearLayout mCancel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_backpopup, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSignOut = view.findViewById(R.id.sign_out);
        mCloseApp = view.findViewById(R.id.closeapp);
        mCancel = view.findViewById(R.id.cancel);
        mSignOut.setOnClickListener(this);
        mCloseApp.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                Fragment fragment = new MySettingFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MyBackPopupFragment.this).commit();
                }
        }
    }




//    /**
//     双击退出
//     */
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                long secondTime = System.currentTimeMillis();
//                if (secondTime - firstTime > 2000) {
//                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                    firstTime = secondTime;
//                    return true;
//                } else {
//                    System.exit(0);
//                }
//                break;
//        }
//        return super.onKeyUp(keyCode, event);
//    }
}