package com.android.ngynstvn.gesturedetectortest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Ngynstvn on 11/23/15.
 */
public class ZoomAndDrag extends View {

    private static final String CLASS_TAG = classTag(MainActivity.class);

    private int currentMode; // either drag or zoom

    // Mode Variables

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    // Zoom values

    private static final float MIN_ZOOM = 0.10F;
    private static final float MAX_ZOOM = 3.00F;

    private ScaleGestureDetector scaleGestureDetector;

    private float scaleFactor = 1.00F;

    // Dragging Variables

    // Initial coordinates (where finger is first placed)
    private float startX = 0.00F;
    private float startY = 0.00F;

    // Final coordinates (where the image will be once dragged)
    private float translateXAmt = 0.00F;
    private float translateYAmt = 0.00F;

    private float previousTranslateX = 0.00F;
    private float previousTranslateY = 0.00F;

    public ZoomAndDrag(Context context) {
        super(context);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        logMethod(CLASS_TAG);

        // Integrating panning and pinch to zoom

        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // The first thing that occurs whenever a user puts the finger on screen.
                currentMode = DRAG;

                // This startX and startY is a result of the position as a result of the previous translation
                startX = event.getX() - previousTranslateX;
                startY = event.getY() - previousTranslateY;

                break;
            case MotionEvent.ACTION_MOVE:
                // No need to set anything. This is already meant for DRAG mode

                // Now set the amount the image is dragged by taking the difference between final
                // and initial positions
                translateXAmt = event.getX() - startX;
                translateYAmt = event.getY() - startY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // When second finger is also on the screen
                // Whenever this happens, change the currentMode
                currentMode = ZOOM;

                break;
            case MotionEvent.ACTION_UP:
                // When all fingers off the screen
                // Because there are no fingers on the screen, there should be no mode
                currentMode = NONE;

                // Whenever our fingers are off the screen, make sure to save the coordinates
                // of the position we are currently at
                previousTranslateX = translateXAmt;
                previousTranslateY = translateYAmt;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // When one finger is moved but the other one is still on the screen
                // After when one takes off the finger, change the currently ZOOM mode back to DRAG
                currentMode = DRAG;

                // Whenever one finger is dragged, at least record where the position is at before
                // switching modes
                previousTranslateX = translateXAmt;
                previousTranslateY = translateYAmt;
                break;
        }

        // This will set value of scaleFactor
        scaleGestureDetector.onTouchEvent(event);

        /**
         *
         * Whatever code goes here
         *
         */

        // If it's in drag mode and scaleFactor does not equal to 0.10F OR
        // current mode is simply ZOOM, invalidate the view --> Redraw canvas
        if((currentMode == DRAG && scaleFactor != MIN_ZOOM) || currentMode == ZOOM) {
            // Re-draw the canvas only in dragging mode
            invalidate();
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);

        /**
         *
         * Canvas Drawing Here
         *
         */

        // Because the image is being scaled via zoom, the dragging scaling has to be impacted as well
        // Divide by the scaleFactor

        canvas.translate((translateXAmt / scaleFactor), (translateYAmt / scaleFactor));
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            logMethod(CLASS_TAG);
            Log.v(CLASS_TAG, "Initial scale factor: " + scaleFactor);
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            Log.v(CLASS_TAG, "Compare scale factor: " + scaleFactor);
            return true;
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
