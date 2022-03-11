package com.iai.ishoes.fragment;

import static com.iai.ishoes.activity.MainActivity.ACTIVE_DISCONNECT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iai.ishoes.R;
import com.iai.ishoes.activity.MainActivity;
import com.iai.ishoes.adapter.GenderSpinnerAdapter;

public class MyAccountFragment extends Fragment implements View.OnClickListener {
    private EditText mPersonalHeight;
    private EditText mPersonalAge;
    private EditText mPersonalWeight;
    private TextView mPersonalGender;
    private Spinner mSpPersonalGender;
    private RelativeLayout mMypersonalBack;
    private Button mChangeMessage;

    private String account;
    private String height;
    private String age;
    private String weight;
    private String gender;

    private GenderSpinnerAdapter spinnerAdapter;
    private String[] genderArray = {"男","女"};

    private boolean isEditing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        initData();
        initView(view);
        back();
        return view;
    }

    private void back() {
        mMypersonalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MyFragment();
                if (!fragment.isAdded()) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.my_fragment, fragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MyAccountFragment.this).commit();
                }
            }
        });
    }
    private void initData() {

    }

    private void initView(View view) {
        mPersonalHeight = view.findViewById(R.id.personal_height);
        mPersonalAge = view.findViewById(R.id.personal_age);
        mPersonalWeight = view.findViewById(R.id.personal_weight);
        mPersonalGender = view.findViewById(R.id.personal_gender);
        mSpPersonalGender = view.findViewById(R.id.sp_personal_gender);
        mMypersonalBack = view.findViewById(R.id.mypersonal_back);
        mChangeMessage = view.findViewById(R.id.change_message);
        mChangeMessage.setOnClickListener(this);

        spinnerAdapter = new GenderSpinnerAdapter(getContext(), genderArray);
        mSpPersonalGender.setAdapter(spinnerAdapter);
        mSpPersonalGender.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_message:
                if(isEditing){
                    mPersonalHeight.setEnabled(false);
                    mPersonalAge.setEnabled(false);
                    mPersonalWeight.setEnabled(false);
                    mPersonalGender.setText(mSpPersonalGender.getSelectedItem().toString());
                    mPersonalGender.setVisibility(View.VISIBLE);
                    mSpPersonalGender.setVisibility(View.GONE);
                    isEditing = !isEditing;
                    mChangeMessage.setText("编辑");
                }else {
                    mPersonalHeight.setEnabled(true);
                    mPersonalAge.setEnabled(true);
                    mPersonalWeight.setEnabled(true);
                    mPersonalGender.setVisibility(View.GONE);
                    mSpPersonalGender.setVisibility(View.VISIBLE);
                    isEditing = !isEditing;
                    mChangeMessage.setText("确认");
                }
        }
    }
}