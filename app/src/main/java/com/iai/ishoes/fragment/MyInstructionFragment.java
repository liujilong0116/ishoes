package com.iai.ishoes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iai.ishoes.R;

public class MyInstructionFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout mBtnMyInstruction;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        initData();
        initView(view);
        back();
        return view;
    }

    private void initView(View view) {
        mBtnMyInstruction = view.findViewById(R.id.btn_my_instruction);
    }

    private void initData() {
    }

    private void back() {
        mBtnMyInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MyFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MyInstructionFragment.this).commit();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}

