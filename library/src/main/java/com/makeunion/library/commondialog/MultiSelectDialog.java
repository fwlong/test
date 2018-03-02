/*
 *  -------------------------------------------------------------------------------------
 *  Mi-Me Confidential
 *
 *  Copyright (C) 2017.  Shanghai Mi-Me Financial Information Service Co., Ltd.
 *  All rights reserved.
 *
 *  No part of this file may be reproduced or transmitted in any form or by any means,
 *  electronic, mechanical, photocopying, recording, or otherwise, without prior
 *  written permission of Shanghai Mi-Me Financial Information Service Co., Ltd.
 *  -------------------------------------------------------------------------------------
 */

package com.makeunion.library.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.makeunion.library.R;

import java.util.ArrayList;
import java.util.List;


public class MultiSelectDialog extends Dialog implements View.OnClickListener {

    public static final int MAX_ITEM_COUNT = 8;

    private Context mContext;

    private MultiSelectListener mListener;

    private RecyclerView mRecyclerView;

    private TextView mConfirmTxt;

    private List<String> mData;

    private MultiSelectAdapter mAdapter;

    private List<Integer> mSelection;

    public MultiSelectDialog(Context context, List<String> data, MultiSelectListener listener) {
        super(context);
        mContext = context;
        if (data == null) {
            mData = new ArrayList<>();
        } else if (data.size() > MAX_ITEM_COUNT) {
            mData = data.subList(0, MAX_ITEM_COUNT);
        } else {
            mData = data;
        }
        mListener = listener;
        setDialogTheme();
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.myDialogStyle);
        initDialog();
    }

    public MultiSelectDialog preSelect(List<Integer> selection) {
        if (selection == null || selection.size() > mData.size()) {
            return this;
        }
        mAdapter.preSelect(selection);
        return this;
    }

    /**
     * set dialog theme(设置对话框主题)
     */
    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
        this.setCanceledOnTouchOutside(true);// 点击外围区域，dialog不可取消
    }

    /**
     * 修改窗口大小
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (displayMetrics.widthPixels);
        dialogWindow.setAttributes(lp);
    }

    private void initDialog() {
        setContentView(R.layout.dialog_select);
        mRecyclerView = (RecyclerView)findViewById(R.id.items_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mConfirmTxt = (TextView)findViewById(R.id.confirm_txt);
        mConfirmTxt.setOnClickListener(this);
        if (mData == null) {
            return;
        }
        mAdapter = new MultiSelectAdapter(mContext, mData);
        mAdapter.setListener(new MultiSelectAdapter.ItemSelectListener() {
            @Override
            public void onItemSelected(List<Integer> selection) {
                mSelection = selection;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemsSelected(mSelection);
        }
        dismiss();
    }

    public interface MultiSelectListener {
        void onItemsSelected(List<Integer> selection);
    }
}