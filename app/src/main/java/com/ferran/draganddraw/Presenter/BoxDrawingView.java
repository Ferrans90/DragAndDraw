package com.ferran.draganddraw.Presenter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ferran.draganddraw.Model.Box;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";
    private static final String ARG_BOX_LIST = "BoxList";
    private static final String ARG_VIEW_PARENT = "ViewParent";

    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;
    private PointF sPoint;

    public BoxDrawingView(Context context) {
        super(context, null);
    }

    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.rotate(box.getAngle());
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
            canvas.rotate(-1F * box.getAngle());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            PointF current = new PointF(event.getX(i), event.getY(i));
            int pointerId = event.getPointerId(i);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (pointerId == 0) {
                        if (mCurrentBox == null) {
                            mCurrentBox = new Box(current);
                            mBoxen.add(mCurrentBox);
                        }
                        break;
                    } else if (pointerId == 1 && mCurrentBox != null) {
                        sPoint = current;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if (pointerId == 0) {
                        mCurrentBox = null;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mCurrentBox != null) {
                        if (pointerId == 0) {
                            mCurrentBox.setCurrent(current);
                            invalidate();
                        } else if (pointerId == 1) {
                            mCurrentBox.setAngle(angleBetweenLines(mCurrentBox.getCurrent().x, mCurrentBox.getCurrent().y,
                                    sPoint.x, sPoint.y,
                                    mCurrentBox.getCurrent().x, mCurrentBox.getCurrent().y,
                                    current.x, current.y));
                            Log.d(TAG, "Angle = " + mCurrentBox.getAngle());
                            invalidate();
                        }
                    }
                    break;
            }
        }
        return true;
    }

    private float angleBetweenLines(float fx, float fy, float sx, float sy, float nfx, float nfy, float nsx, float nsy) {
        float angle1 = (float) Math.atan2(sy - fy, sx - fx);
        float angle2 = (float) Math.atan2(nsy - nfy, nsx - nfx);

        float angle = (float) (Math.toDegrees(angle2 - angle1) % 360);
        if (angle < 0f) {
            angle += 360f;
        }
        return angle;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i(TAG, "rotate");
        Bundle items = new Bundle();
        items.putSerializable(ARG_BOX_LIST, (Serializable) mBoxen);
        items.putParcelable(ARG_VIEW_PARENT, super.onSaveInstanceState());
        return items;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mBoxen = (List<Box>) bundle.getSerializable(ARG_BOX_LIST);
            state = bundle.getParcelable(ARG_VIEW_PARENT);
        }
        super.onRestoreInstanceState(state);
    }
}
