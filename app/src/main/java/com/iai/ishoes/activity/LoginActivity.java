package com.iai.ishoes.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.iai.ishoes.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button LoginButton;
    private long firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);    // 去掉标题栏
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        LoginButton = (Button)findViewById(R.id.btn_login);
        LoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:{
                System.out.println("123");

            }
            break;
            default:
                break;
        }
    }
}
