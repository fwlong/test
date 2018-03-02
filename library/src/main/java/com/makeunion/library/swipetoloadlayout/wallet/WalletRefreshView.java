/*
 * -------------------------------------------------------------------------------------
 *     Mi-Me Confidential
 *
 *     Copyright (C) 2015 Shanghai Mi-Me Financial Information Service Co., Ltd.
 *     All rights reserved.
 *
 *     No part of this file may be reproduced or transmitted in any form or by any means,
 *     electronic, mechanical, photocopying, recording, or otherwise, without prior
 *     written permission of Shanghai Mi-Me Financial Information Service Co., Ltd.
 *
 * -------------------------------------------------------------------------------------
 */

package com.makeunion.library.swipetoloadlayout.wallet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 钱包样式刷新View
 *
 * @author renjialiang
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class WalletRefreshView extends RefreshableView {

    public static final int ANIMATION_STATE_ARC = 0;
    public static final int ANIMATION_STATE_CIRCLE = 1;
    public static final int ANIMATION_STATE_POINT = 2;
    public static final int POINT_NUM_CONFIG[] = new int[]{24, 12, 6, 5, 4, 3, 2, 1, 0};
    private static final long NUMBER_CHANGE_DURATION = 20;
    TimerTask task;
    private boolean isRunning;
    private float mPercent;
    private Paint mArcPaint;
    private int mPointState;
    private RectF mDrawRect;
    private Point mDrawCenter;
    private Timer mTimer;
    private long mPassedTime;
    private int mAnimationRepeatDuration;
    private int mArcDuration;
    private int mAnimationState = -1;
    private int mCircleDuration;
    private int mDurationPerFrame;
    private long mStartAngle;
    private float mDrawRadius;
    private boolean isAnimation;
    private Paint mPointPaint;
    private boolean isBeingDragged;
    private int mRefreshColor;

    public WalletRefreshView(Context context) {
        this(context, null);
    }

    public WalletRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    private void init() {
        initPaint();
        initDrawRect();
    }

    private void initDrawRect() {
        int radio = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        int centerX = getMeasuredWidth() / 2;
        int left = (int) (centerX - radio);
        int right = (int) (centerX + radio);
        int centerY = getMeasuredHeight() / 2;
        int top = centerY - radio;
        int bottom = centerY + radio;

        mDrawRect = new RectF(left, top, right, bottom);
        mDrawCenter = new Point(centerX, centerY);

        mDrawRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
    }

    private void initPaint() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStrokeWidth(4);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStrokeWidth(1f);
        mPointPaint.setStyle(Paint.Style.FILL);
        if (mRefreshColor != 0) {
            mArcPaint.setColor(mRefreshColor);
            mPointPaint.setColor(mRefreshColor);
        } else {
            mPointPaint.setColor(Color.WHITE);
            mArcPaint.setColor(Color.WHITE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRunning) {
            switch (mAnimationState) {
                case ANIMATION_STATE_ARC:
                    drawArc(canvas);
                    break;
                case ANIMATION_STATE_CIRCLE:
                    drawCircle(canvas);
                    break;
                case ANIMATION_STATE_POINT:
                    drawPoint(canvas);
                    break;
                default:
                    break;
            }
        } else if (isBeingDragged) {
            drawArc(canvas);
        }
    }

    private void drawPoint(Canvas canvas) {
        if (mPointState >= 9 || mPointState < 0) {
            return;
        }
        int pointNum = POINT_NUM_CONFIG[mPointState];
        if (pointNum != 0) {
            int anglePrePoint = 360 / pointNum;
            for (int i = 0; i < pointNum; i++) {
                double angRad = (mStartAngle + anglePrePoint * i) * Math.PI / 180;
                float pointY = (float) (mDrawCenter.y - Math.sin(angRad) * mDrawRadius);
                float pointX = (float) (mDrawCenter.x + Math.cos(angRad) * mDrawRadius);
                canvas.drawCircle(pointX, pointY, 2.5f, mPointPaint);
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawOval(mDrawRect, mArcPaint);
    }

    private void drawArc(Canvas canvas) {
        float drawAngle = mPercent * 360;
        canvas.drawArc(mDrawRect, -90, drawAngle, false, mArcPaint);
    }

    @Override
    public void setPercent(float percent) {
        this.mPercent = percent;
        invalidate();
    }

    @Override
    void setAnimationDuration(int repeatDuration) {
        mArcDuration = repeatDuration / 4;
        mCircleDuration = repeatDuration / 2;
        mDurationPerFrame = repeatDuration / 18;
        mAnimationRepeatDuration = mCircleDuration + mDurationPerFrame * 9;
    }

    @Override
    public void start() {
        if (isRunning) {
            return;
        }
        mTimer = new Timer();
        isRunning = true;
        mPassedTime = mArcDuration;
        task = new LoopTask(this);
        mTimer.schedule(task, 0, NUMBER_CHANGE_DURATION);
    }

    private void postTask() {
        if (!isRunning) {
            stop();
        }
        if (mPassedTime <= mArcDuration) {
            mPercent = mPassedTime * 1.0f / mArcDuration;
            mAnimationState = ANIMATION_STATE_ARC;
        } else if (mPassedTime <= mCircleDuration) {
            mAnimationState = ANIMATION_STATE_CIRCLE;
        } else {
            mAnimationState = ANIMATION_STATE_POINT;
            mPointState = (int) ((mPassedTime - mCircleDuration) / mDurationPerFrame);
            mStartAngle = (mPassedTime - mCircleDuration) / 2;
        }
        mPassedTime += NUMBER_CHANGE_DURATION;
        mPassedTime = mPassedTime % mAnimationRepeatDuration;
        postInvalidate();
    }

    private static class LoopTask extends TimerTask {
        private WeakReference<WalletRefreshView> mRefreshView;

        public LoopTask(WalletRefreshView refreshView) {
            mRefreshView = new WeakReference<>(refreshView);
        }

        @Override
        public void run() {
            WalletRefreshView walletRefreshView = mRefreshView.get();
            if (walletRefreshView != null) {
                walletRefreshView.postTask();
            }
        }
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    public boolean isAnimation() {
        return isAnimation;
    }

    @Override
    public void setIsAnimation(boolean isAnimation) {
        this.isAnimation = isAnimation;
    }

    @Override
    public void setIsBeingDragged(boolean isBeingDragged) {
        this.isBeingDragged = isBeingDragged;
    }

    @Override
    public void setRefreshColor(int refreshColor) {
        this.mRefreshColor = refreshColor;
        if (mPointPaint != null) {
            mPointPaint.setColor(refreshColor);
        }
        if (mArcPaint != null) {
            mArcPaint.setColor(refreshColor);
        }
    }

}
