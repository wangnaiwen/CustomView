package com.wnw.wnw.customviewtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wnw.wnw.customviewtest.R;

/**
 *
 * @author wnw
 *
 * @date 2017/11/2 0002 10:55
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView v01 = (TextView)findViewById(R.id.v01);
        TextView v02 = (TextView)findViewById(R.id.v02);
        v01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CustomActivity01.class));
            }
        });

        v02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CustomActivity02.class));
            }
        });
    }

}
