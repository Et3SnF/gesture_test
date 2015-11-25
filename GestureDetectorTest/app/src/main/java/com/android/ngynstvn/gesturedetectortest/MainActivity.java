package com.android.ngynstvn.gesturedetectortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String CLASS_TAG = classTag(MainActivity.class);

    private TouchView testImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logMethod(CLASS_TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testImage = (TouchView) findViewById(R.id.tchv_find_waldo);
        testImage.setBackground(getDrawable(R.drawable.find_waldo));
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
