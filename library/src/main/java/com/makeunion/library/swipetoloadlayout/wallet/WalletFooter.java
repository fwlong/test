package com.makeunion.library.swipetoloadlayout.wallet;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.makeunion.library.R;
import com.makeunion.library.swipetoloadlayout.SwipeLoadMoreFooterLayout;


/**
 * 钱包样式刷新Footer
 *
 * @author renjialiang
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class WalletFooter extends SwipeLoadMoreFooterLayout {

    private WalletRefreshView mWalletView;

    private int mFooterHeight;

    private int mRefreshColor;

    public WalletFooter(Context context) {
        this(context, null);
    }

    public WalletFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WalletFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWalletView = (WalletRefreshView)findViewById(R.id.swipe_to_load_wallet_load_view);
        mWalletView.setAnimationDuration(2000);
        mWalletView.setRefreshColor(mRefreshColor == 0 ?
                ContextCompat.getColor(getContext(), R.color.colorRefresh) : mRefreshColor);
        onNormalStatus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mFooterHeight = getMeasuredHeight();
    }

    @Override
    public void onPrepare() {
        onNormalStatus();
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (-y >= mFooterHeight) {
                onCanReleaseStatus();
            } else {
                onPullUpStatus(y);
            }
        }
    }

    @Override
    public void onLoadMore() {
        onLoadStatus();
    }

    @Override
    public void onRelease() {
        onLoadStatus();
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

    private void onPullUpStatus(int y) {
        float percent = Math.abs(y * 1.0f / mFooterHeight);
        mWalletView.setIsBeingDragged(true);
        mWalletView.setPercent(percent);
    }

    private void onCanReleaseStatus() {
        mWalletView.setIsBeingDragged(true);
        mWalletView.start();
    }

    private void onLoadStatus() {
        mWalletView.setIsBeingDragged(false);
        onLoadStatusNoCallBack();
    }

    private void onBackNormalStatus() {
        mWalletView.setIsBeingDragged(false);
        mWalletView.setPercent(0);
        mWalletView.stop();
    }

    private void onBackLoadStatus() {
        mWalletView.setIsBeingDragged(true);
    }

    /**
     * 只能被‘onLoadStatus’调用
     */
    private void onLoadStatusNoCallBack() {
        mWalletView.setIsBeingDragged(false);
    }

    public int getRefreshColor() {
        return mRefreshColor;
    }

    public void setRefreshColor(int refreshColor) {
        mRefreshColor = refreshColor;
    }
}
