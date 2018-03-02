package com.makeunion.library.swipetoloadlayout.wallet;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.makeunion.library.R;
import com.makeunion.library.swipetoloadlayout.SwipeRefreshHeaderLayout;


/**
 * 钱包样式刷新Header
 *
 * @author renjialiang
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class WalletHeader extends SwipeRefreshHeaderLayout {

    private WalletRefreshView mWalletView;

    private int mHeaderHeight;

    private int mRefreshColor;

    public WalletHeader(Context context) {
        this(context, null);
    }

    public WalletHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WalletHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWalletView = (WalletRefreshView)findViewById(R.id.swipe_to_load_wallet_refresh_view);
        mWalletView.setAnimationDuration(2000);
        mWalletView.setRefreshColor(mRefreshColor == 0 ?
                ContextCompat.getColor(getContext(), R.color.colorRefresh) : mRefreshColor);
        onNormalStatus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeaderHeight = getMeasuredHeight();
    }

    @Override
    public void onPrepare() {
        onNormalStatus();
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (y > mHeaderHeight) {
                onCanRefreshStatus();
            } else if (y < mHeaderHeight) {
                onPullDownStatus(y);
            }
        }
    }

    @Override
    public void onRelease() {
        onRefreshStatus();
    }

    @Override
    public void onRefresh() {
        onRefreshStatus();
    }

    @Override
    public void onComplete() {
        onBackNormalStatus();
    }

    @Override
    public void onReset() {
        onBackNormalStatus();
    }

    private void onNormalStatus() {
        mWalletView.setIsBeingDragged(false);
        mWalletView.setPercent(0);
        mWalletView.stop();
    }

    private void onPullDownStatus(int y) {
        mWalletView.setIsBeingDragged(true);
        float percent = Math.abs(y * 1.0f / mHeaderHeight);
        mWalletView.setPercent(percent);
    }

    private void onCanRefreshStatus() {
        mWalletView.setIsBeingDragged(true);
    }

    private void onRefreshStatus() {
        mWalletView.setIsBeingDragged(false);
        mWalletView.start();
    }

    private void onBackNormalStatus() {
        mWalletView.setIsBeingDragged(false);
        mWalletView.setPercent(0);
        mWalletView.stop();
    }

    private void onBackRefreshStatus() {
        mWalletView.setIsBeingDragged(true);
    }

    private void onAutoRefreshStatus() {
        onRefreshStatus();
    }

    public int getRefreshColor() {
        return mRefreshColor;
    }

    public void setRefreshColor(int refreshColor) {
        mRefreshColor = refreshColor;
    }
}