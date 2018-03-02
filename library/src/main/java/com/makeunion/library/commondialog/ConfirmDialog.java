package com.makeunion.library.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.makeunion.library.R;


public class ConfirmDialog extends Dialog implements View.OnClickListener {

    private TextView mTitleTxt;

    private TextView mCancelTxt;

    private TextView mConfirmTxt;

    private ConfirmListener mListener;

    public ConfirmDialog(Context context, ConfirmListener listener) {
        super(context);
        mListener = listener;
        setDialogTheme();
        getWindow().setGravity(Gravity.CENTER);

        initDialog();
    }

    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
        this.setCanceledOnTouchOutside(true);// 点击外围区域，dialog不可取消
    }

    private void initDialog() {
        setContentView(R.layout.dialog_confirm);
        mTitleTxt = (TextView) findViewById(R.id.title_txt);
        mCancelTxt = (TextView) findViewById(R.id.cancel_txt);
        mConfirmTxt = (TextView) findViewById(R.id.confirm_txt);
        mCancelTxt.setOnClickListener(this);
        mConfirmTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirm_txt) {
            if (mListener != null) {
                mListener.onConfirm();
            }
        } else {
            if (mListener != null) {
                mListener.onCancel();
            }
        }
        dismiss();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (displayMetrics.widthPixels);
        dialogWindow.setAttributes(lp);
    }

    public ConfirmDialog title(String title) {
        mTitleTxt.setText(title);
        return this;
    }

    public ConfirmDialog title(int resId) {
        mTitleTxt.setText(resId);
        return this;
    }

    public void setListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    public interface ConfirmListener {
        void onConfirm();
        void onCancel();
    }
}