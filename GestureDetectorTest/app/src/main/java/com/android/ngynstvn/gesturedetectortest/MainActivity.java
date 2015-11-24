package com.android.ngynstvn.gesturedetectortest;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String CLASS_TAG = classTag(MainActivity.class);

    private ImageView testImage;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix; // will allow the motion effect to happen
    private static float scaleFactor = 1.00F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testImage =(ImageView)findViewById(R.id.iv_find_waldo);

        // This is important or else it won't work
        testImage.setScaleType(ImageView.ScaleType.MATRIX);

        matrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Picasso.with(getApplicationContext()).load(R.drawable.find_waldo).into(testImage);
    }

    // This overridden method is important so that the overall gesture detector can activate

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        logMethod(CLASS_TAG);
        // Make sure you associate detector with this onTouchEvent() method
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    // private class that implements OnScaleGestureListener

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            logMethod(CLASS_TAG);
            // Get a variable for a scale factor
            scaleFactor = detector.getScaleFactor();

            // Ensures the scale factor can never ever exceed 3.00x and never goes below 0.1x
            // Outside gets the max, something guaranteed to be greater than 0.1x
            // The min guarantees that the value can never exceed 3.00x
            scaleFactor = Math.max(0.10F, Math.min(scaleFactor, 3.00F));

            // Attach this scale factor in to factor so the scale can take effect
            // Ensures that both the x and y scale proportionately.
            matrix.setScale(scaleFactor, scaleFactor);

            // Attach this matrix to the imageView that you want to do some effect
            testImage.setImageMatrix(matrix);

            return true; // if false, it will just not execute this and stop any subsequent things
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            logMethod(CLASS_TAG);
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            logMethod(CLASS_TAG);
        }
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
