package com.wnw.wnw.customviewtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wnw.wnw.customviewtest.R;
import com.wnw.wnw.customviewtest.view.CustomView02;

/**
 * @author wnw
 * @date 2017/11/3 0003 17:33
 */
public class CustomActivity02 extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom02);
        CustomView02 view = (CustomView02)findViewById(R.id.bell);

        view.setTime(0, 12);
    }
}
