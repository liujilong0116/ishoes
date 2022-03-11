package com.iai.ishoes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iai.ishoes.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private ArrayList<String> mData;
    private Context context;
    private OnRecycleViewItemClickListener mOnRecycleViewItemClickListener;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    public MyRecycleViewAdapter(Context context, ArrayList<String> mData) {
        this.context = context;
        this.mData = mData;
        isClicks = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            isClicks.add(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_recycleview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mTv.setText(mData.get(position));
        holder.itemView.setTag(holder.mTv);
        if (isClicks.get(position)) {
            holder.mTv.setTextColor(Color.rgb(0, 145, 222));
        } else {
            holder.mTv.setTextColor(Color.rgb(74, 74, 74));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnRecycleViewItemClickListener && null != view) {
                    for (int i = 0; i < isClicks.size(); i++) {
                        isClicks.set(i, false);
                    }
                    isClicks.set(position, true);
                    notifyDataSetChanged();
                    mOnRecycleViewItemClickListener.onItemClick(view, mData, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnRecycleViewItemClickListener {
        void onItemClick(View view, Object object, int position);
    }

    public void setOnItemClickListener(OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.mOnRecycleViewItemClickListener = onRecycleViewItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.my_item_tv);
        }
    }
}
