package android.mobileapp.qrcode.view.dialog.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.mobileapp.qrcode.custom.pdfviewer.PDFView;
import android.mobileapp.qrcode.helper.Constants;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class CustomZoomViewAdapter extends FrameLayout implements GestureDetector.OnGestureListener {

    /*
    *********************************************
     ----------------------------------
     CHECKLIST
     ----------------------------------
     Finish:
     - PINCH
     - DOUBLE TAP (ZOOM IN - OUT)
     - PAN
     ----------------------------------
     *********************************************/
    /**
     * Save the latest scale digit
     */
    private float mSavedScale = Constants.MIN_SCALE;

    /**
     * Gesture listener for double tap.
     */
    private GestureDetector mGestureListener;

    /**
     * The zooming rate by x.
     */
    private float mScaleX = Constants.MIN_SCALE;

    /**
     * X position of event - when touching on screen.
     */
    private float mX1 = 0;

    /**
     * Y position of event - when touching on screen.
     */
    private float mY1 = 0;

    /**
     * Mode when touch on screen.
     */
    private int mMode = NONE;

    /**
     * Touch on screen but no or zoom.
     */
    private static final int NONE = 0;

    /**
     * Touch on screen - drag action (pan).
     */
    private static final int DRAG = 1;

    /**
     * Touch on screen - zoom action.
     */
    private static final int ZOOM = 2;

    /**
     * Old distance between 2 fingers.
     */
    private float mOldDist;

    /**
     * The main layout
     */
    private View mMainView;

    /**
     * The left margin of mMainView after being zoomed
     */
    private int mLeftMarginAfterZoom = 0;

    /**
     * The top margin of mMainView after being zoomed
     */
    private int mTopMarginAfterZoom = 0;

    /**
     * Save the ordinal of finger (point) with X coordinate
     */
    private int mSaveMaxX;

    /**
     * Save the ordinal of finger (point) with Y coordinate
     */
    private int mSaveMaxY;

    private PDFView.GestureEventInterface gestureEventInterface;

    public void setGestureEventInterface(PDFView.GestureEventInterface gestureEventInterface) {
        this.gestureEventInterface = gestureEventInterface;
    }

    /**
     * CONSTRUCTOR
     */
    public CustomZoomViewAdapter(Context context, View childView) {
        super(context);
        //noinspection deprecation
        mGestureListener = new GestureDetector(this);
        this.mMainView = childView;
        childView.setOnTouchListener(mainViewTouched);
    }

    public CustomZoomViewAdapter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Zooming the layout.
     *
     * @param scaleX - the scale x
     * @param scaleY - the scale Y
     * @param pivot  - the point to start
     */
    private void zoom(final Float scaleX, final Float scaleY, final PointF pivot) {
        // Set the start position when zooming layout
        mMainView.setPivotX(pivot.x);
        mMainView.setPivotY(pivot.y);
        // Set the zooming scale for layout
        mMainView.setScaleX(scaleX);
        mMainView.setScaleY(scaleY);
        // Set the zooming scale
        mScaleX = scaleX;
    }

    /**
     * Space between two fingers in the first touching.
     *
     * @param event - motion event
     * @return the distance between 2 fingers
     */
    private float spacing(final MotionEvent event) {
        // Calculate the space between 2 fingers
        float x = (event.getX(0) - event.getX(1)) * mScaleX;
        float y = (event.getY(0) - event.getY(1)) * mScaleX;
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Check MAX the X coordinate of fingers (for preventing crossed move)
     *
     * @param event MotionEvent
     * @return the ordinal of finger
     */
    private int maxPointX(MotionEvent event) {
        return event.getX(0) > event.getX(1) ? 0 : 1;
    }

    /**
     * Check MAX the Y coordinate of fingers (for preventing crossed move)
     *
     * @param event MotionEvent
     * @return the ordinal of finger
     */
    private int maxPointY(MotionEvent event) {
        return event.getY(0) > event.getY(1) ? 0 : 1;
    }

    private OnTouchListener mainViewTouched = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            /*
             * Double tap to ZOOM IN (OUT)
             */
            mGestureListener.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                public boolean onSingleTapConfirmed(final MotionEvent e) {
                    return false;
                }

                public boolean onDoubleTapEvent(final MotionEvent e) {
                    return false;
                }

                public boolean onDoubleTap(final MotionEvent e) {
                    if (mScaleX >= Constants.MAX_SCALE) {
                        resetDoubleTapZoom();
                    } else if (mScaleX < Constants.MID_SCALE) {
                        doubleTapZoom(Constants.MID_SCALE, new PointF(mMainView.getWidth() / 2, mMainView.getHeight() / 2));
                    } else {
                        doubleTapZoom(Constants.MAX_SCALE, new PointF(mMainView.getWidth() / 2, mMainView.getHeight() / 2));
                    }
                    return true;
                }
            });

            /*
             * PAN, PINCH
             */
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            if (event.getPointerCount() <= 2) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    // One finger touch
                    case MotionEvent.ACTION_DOWN:
                        // Position of event at first touch
                        mX1 = event.getRawX();
                        mY1 = event.getRawY();
                        mMode = DRAG;
                        break;

                    // One finger leave
                    case MotionEvent.ACTION_UP:
                        break;

                    // One or more finger touch
                    case MotionEvent.ACTION_POINTER_UP:
                        // A finger leaves the screen but at least one finger
                        mSavedScale = mScaleX;
                        mMode = NONE;
                        mLeftMarginAfterZoom = (int) (mMainView.getWidth() * (mScaleX - 1)) / 2;
                        mTopMarginAfterZoom = (int) (mMainView.getHeight() * (mScaleX - 1)) / 2;
                        break;

                    // One or more finger leave (still have one finger touch)
                    case MotionEvent.ACTION_POINTER_DOWN:
                        // The second finger touches the screen
                        mOldDist = spacing(event);
                        mMode = ZOOM;
                        mSaveMaxX = maxPointX(event);
                        mSaveMaxY = maxPointY(event);
                        break;

                    // Finger(s) move
                    case MotionEvent.ACTION_MOVE:
                        // PAN
                        // Get the operation of left margin (1 or -1)
                        int operationLeftMargin;
                        // Get the operation of top margin (1 or -1)
                        int operationTopMargin;
                        if (mMode == DRAG) {
                            float x2 = event.getRawX();
                            float y2 = event.getRawY();

                            lp.leftMargin += (int) (x2 - mX1);
                            lp.topMargin += (int) (y2 - mY1);

                            operationLeftMargin = lp.leftMargin > 0 ? 1 : -1;
                            operationTopMargin = lp.topMargin > 0 ? 1 : -1;

                            /*
                             *  Lock the position of childview layout
                             *  TOP LEFT - TOP RIGHT - BOTTOM LEFT - BOTTOM RIGHT
                             */
                            lp.leftMargin = (Math.abs(lp.leftMargin) > mLeftMarginAfterZoom ? mLeftMarginAfterZoom * operationLeftMargin : lp.leftMargin);
                            lp.topMargin = (Math.abs(lp.topMargin) > mTopMarginAfterZoom ? mTopMarginAfterZoom * operationTopMargin : lp.topMargin);

                            lp.rightMargin = -lp.leftMargin;
                            lp.bottomMargin = -lp.topMargin;
                            mX1 = x2;
                            mY1 = y2;
                            // Update new location for childView layout
                            mMainView.setLayoutParams(lp);
                        }

                        /*
                         * PINCH to zoom
                         */
                        if (mMode == ZOOM) {
                            // 2 fingers not crossed
                            if (maxPointX(event) == mSaveMaxX && maxPointY(event) == mSaveMaxY) {
                                // Space between the two fingers in the first touching
                                float newD = spacing(event);
                                float scale = 0;

                                // Zoom IN
                                if (newD > mOldDist) {
                                    Log.i("Eric Test", "ZOOMING IN");
                                    scale = (newD / mOldDist) - 1 + mSavedScale;
                                }
                                // Zoom OUT
                                if (newD < mOldDist) {
                                    Log.i("Eric Test", "ZOOMING OUT");
                                    scale = (newD / mOldDist) * mSavedScale;
                                }

                                if (scale <= Constants.MIN_SCALE) {
                                    scale = Constants.MIN_SCALE;
                                } else if (scale >= Constants.MAX_SCALE) {
                                    scale = Constants.MAX_SCALE;
                                }

                                // Check scale before applying
                                float leftMarginAfterZoomTemp = (int) (mMainView.getWidth() * (scale - 1)) / 2;
                                float topMarginAfterZoomTemp = (int) (mMainView.getHeight() * (scale - 1)) / 2;
                                float zoomPivotX = mMainView.getWidth() / 2;
                                float zoomPivotY = mMainView.getHeight() / 2;

                                operationLeftMargin = lp.leftMargin > 0 ? 1 : -1;
                                operationTopMargin = lp.topMargin > 0 ? 1 : -1;

                                if (Math.abs(lp.leftMargin) >= leftMarginAfterZoomTemp) {
                                    zoomPivotX += operationLeftMargin / 2 * (Math.abs(lp.leftMargin) - leftMarginAfterZoomTemp);
                                    lp.leftMargin = (int) leftMarginAfterZoomTemp * operationLeftMargin;
                                }
                                if (Math.abs(lp.topMargin) >= topMarginAfterZoomTemp) {
                                    zoomPivotY += operationTopMargin / 2 * (Math.abs(lp.topMargin) - topMarginAfterZoomTemp);
                                    lp.topMargin = (int) topMarginAfterZoomTemp * operationTopMargin;
                                }

                                // Zooming rate > 1
                                zoom(scale, scale, new PointF(zoomPivotX, zoomPivotY));
                                // Update new location for childView layout
                                lp.rightMargin = -lp.leftMargin;
                                lp.bottomMargin = -lp.topMargin;
                                mMainView.setLayoutParams(lp);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            mGestureListener.onTouchEvent(event);
            gestureEventInterface.handleEndScroll(event);
            return true;
        }
    };

    // Double tap zoom
    private void doubleTapZoom(float scale, PointF pivot) {
        zoom(scale, scale, pivot);
        mLeftMarginAfterZoom = (int) (mMainView.getWidth() * (mScaleX - 1)) / 2;
        mTopMarginAfterZoom = (int) (mMainView.getHeight() * (mScaleX - 1)) / 2;
        mSavedScale = mScaleX = scale;
    }

    // Reset zoom by double tap
    private void resetDoubleTapZoom() {
        zoom(Constants.MIN_SCALE, Constants.MIN_SCALE, new PointF(0, 0));
        mLeftMarginAfterZoom = 0;
        mTopMarginAfterZoom = 0;
        mSavedScale = mScaleX = Constants.MIN_SCALE;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mMainView.getLayoutParams();
        lp.leftMargin = 0;
        lp.rightMargin = 0;
        lp.topMargin = 0;
        lp.bottomMargin = 0;
        mMainView.setLayoutParams(lp);
    }

    /**
     * UNIMPLEMENTED methods
     */

    @Override
    public boolean onDown(MotionEvent e) {
        return gestureEventInterface.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return gestureEventInterface.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return gestureEventInterface.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}