package com.ferran.draganddraw.Model;

import android.graphics.PointF;

import java.io.Serializable;

public class Box implements Serializable {
    private PointF mOrigin;
    private PointF mCurrent;
    private float mAngle;

    public Box() {

    }

    public Box(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
        mAngle = 0;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF mCurrent) {
        this.mCurrent = mCurrent;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
    }
}
