package com.android.ngynstvn.gesturedetectortest;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Ngynstvn on 11/24/15.
 */

public class TouchView extends View {

    private static final String CLASS_TAG = classTag(TouchView.class);

    // For simple touching events

    private static final int INVALID_POINTER_ID = -1;

    private Drawable testImage;
    private float positionX;
    private float positionY;

    private float previousTouchX;
    private float previousTouchY;

    private int activePointerId = INVALID_POINTER_ID;

    // For Pinch to Zoom events

    private static final float MIN_ZOOM = 0.10F;
    private static final float MAX_ZOOM = 3.00F;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.00F;

    // Constructor

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TouchView(Context context) {
        super(context);
        testImage = context.getDrawable(R.drawable.find_waldo);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        testImage = context.getDrawable(R.drawable.find_waldo);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        testImage = context.getDrawable(R.drawable.find_waldo);
        testImage.setBounds(0, 0, testImage.getIntrinsicWidth(), testImage.getIntrinsicHeight());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TouchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        testImage = context.getDrawable(R.drawable.find_waldo);
        testImage.setBounds(0, 0, testImage.getIntrinsicWidth(), testImage.getIntrinsicHeight());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch(action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();

                // Record where the finger last touched on the screen
                previousTouchX = x;
                previousTouchY = y;

                // Record the pointer ID of the finger that's on the screen at the moment
                activePointerId = event.getPointerId(0);

                break;
            }

            case MotionEvent.ACTION_MOVE: {

                // Find index of latest pointer on the screen, then record its position
                final int pointerIndex = event.findPointerIndex(activePointerId);

                // Get the position on the screen with the pointer ID taken into account
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);

                // Calculate the distance moved;
                final float dx = x - previousTouchX;
                final float dy = y - previousTouchY;

                // Move the object
                positionX += dx;
                positionY += dy;

                // Again, record last touched position to prepare for next event
                previousTouchX = x;
                previousTouchY = y;

                // Invalidate in order to request a redraw
                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP: {
                // The moment that a finger is off the screen, make any pointer ID invalid.
                activePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                // same thing here. make the pointer id invalid
                activePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                // This event activates whenever a user has two fingers on the screen but one is removed
                // while the other one stays.

                // Extract the index of pointer that left the screen
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);

                if(pointerId == activePointerId) {
                    // This is the pointer Id of the finger removed on the screen. Once it's removed,
                    // reassign a new pointer and adjust accordingly.

                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;

                    // Record the position of the new pointer index
                    previousTouchX = event.getX(newPointerIndex);
                    previousTouchY = event.getY(newPointerIndex);

                    activePointerId = event.getPointerId(newPointerIndex);
                }

                break;
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(positionX, positionY);
        testImage.draw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Set the limits of the zoom factor
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));

            // be sure to invalidate the view because every time the image is zoomed, redraw
            invalidate();

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