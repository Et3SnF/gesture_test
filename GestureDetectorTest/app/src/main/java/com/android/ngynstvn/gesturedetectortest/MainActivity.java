package com.android.ngynstvn.gesturedetectortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String CLASS_TAG = classTag(MainActivity.class);

    private ImageView testImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logMethod(CLASS_TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testImage = (ImageView) findViewById(R.id.iv_find_waldo);
        testImage.setScaleType(ImageView.ScaleType.MATRIX);
        Picasso.with(getApplicationContext()).load(R.drawable.find_waldo).into(testImage);
    }

    // Logging methods

    public static String classTag(Class className) {
        return "(" + className.getSimpleName() + "): ";
    }

    public static void logMethod(String className) {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String message = stackTraceElements[3].getMethodName() + "() called | Thread: " + Thread.currentThread().getName();
        Log.e(className, message);
    }

    public static void logMethod(String className, String additional) {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String classTag = "(" + className + ")";
        String message = additional + "'s " + stackTraceElements[3].getMethodName() + "() called | Thread: " + Thread.currentThread().getName();
        Log.e(classTag, message);
    }
}
