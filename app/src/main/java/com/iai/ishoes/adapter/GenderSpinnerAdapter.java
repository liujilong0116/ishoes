package com.iai.ishoes.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iai.ishoes.R;


public class GenderSpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] mStringArray;

    public GenderSpinnerAdapter(Context context, String[] stringArray) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        mContext = context;
        mStringArray = stringArray;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(mStringArray[position]);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(mStringArray[position]);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20f);
        return convertView;
    }
}
