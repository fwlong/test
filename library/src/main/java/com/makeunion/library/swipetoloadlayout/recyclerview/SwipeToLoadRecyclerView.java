package com.makeunion.library.swipetoloadlayout.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeunion.library.R;
import com.makeunion.library.swipetoloadlayout.SwipeToLoadLayout;
import com.makeunion.library.swipetoloadlayout.wallet.WalletRefreshView;


/**
 * RecyclerView集成版.
 *
 * @author renjialiang
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SwipeToLoadRecyclerView extends SwipeToLoadLayout {

    private RecyclerView mRecyclerView;

    private View mHeaderView;

    private View mFooterView;

    private LayoutInflater mInflater;

    private int mSwipeColor;

    private int mHeaderHeight;

    private int mFooterHeight;

    public SwipeToLoadRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeToLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeToLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        setSwipeStyle(STYLE.CLASSIC);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SwipeToLoadRecyclerView, defStyleAttr, 0);
        try {
            mSwipeBackgroundColor = a.getColor(R.styleable.SwipeToLoadRecyclerView_swipe_background,
                    Color.WHITE);
            mSwipeColor = a.getColor(R.styleable.SwipeToLoadRecyclerView_swipe_color,
                    Color.DKGRAY);
            mHeaderHeight = a.getDimensionPixelSize(R.styleable.SwipeToLoadRecyclerView_header_height,
                    context.getResources().getDimensionPixelSize(R.dimen.header_height));
            mFooterHeight = a.getDimensionPixelSize(R.styleable.SwipeToLoadRecyclerView_footer_height,
                    context.getResources().getDimensionPixelSize(R.dimen.footer_height));
        } finally {
            if (a != null) {
                a.recycle();
            }
        }

        initViews();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void initViews() {
        initHeader();
        initRecyclerView();
        initFooter();
    }

    private void initHeader() {
        mHeaderView = mInflater.inflate(R.layout.swipe_to_load_wallet_header, this, false);
        mHeaderView.setBackgroundColor(mSwipeBackgroundColor);
        WalletRefreshView refreshView = (WalletRefreshView)mHeaderView
                .findViewById(R.id.swipe_to_load_wallet_refresh_view);
        refreshView.setRefreshColor(mSwipeColor);
        ViewGroup.MarginLayoutParams headerLp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerLp.height = mHeaderHeight;
        this.addView(mHeaderView, headerLp);
    }

    private void initRecyclerView() {
        mRecyclerView = new RecyclerView(getContext());
        ViewGroup.MarginLayoutParams recyclerViewLp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mRecyclerView, recyclerViewLp);
    }

    private void initFooter() {
        mFooterView = mInflater.inflate(R.layout.swipe_to_load_wallet_footer, this, false);
        mFooterView.setBackgroundColor(mSwipeBackgroundColor);
        WalletRefreshView loadView = (WalletRefreshView)mFooterView
                .findViewById(R.id.swipe_to_load_wallet_load_view);
        loadView.setRefreshColor(mSwipeColor);
        ViewGroup.MarginLayoutParams footerLp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footerLp.height = mFooterHeight;
        this.addView(mFooterView, footerLp);
    }
}
