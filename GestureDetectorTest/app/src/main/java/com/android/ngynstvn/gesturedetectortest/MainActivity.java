package com.android.ngynstvn.gesturedetectortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView testImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tb_activity_main);
        testImage = (ImageView) findViewById(R.id.iv_test_image);
        setSupportActionBar(toolbar);

        Picasso.with(getApplicationContext()).load(R.drawable.find_waldo).into(testImage);
    }
}
