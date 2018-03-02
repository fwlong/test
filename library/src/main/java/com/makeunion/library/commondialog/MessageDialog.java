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


public class MessageDialog extends Dialog implements View.OnClickListener {

    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_LEFT = 2;
    public static final int GRAVITY_RIGHT = 3;

    private Context mContext;

    private TextView mTitleTxt;

    private TextView mCancelTxt;

    private TextView mConfirmTxt;

    private TextView mMessageTxt;

    private String mMessage;

    private ConfirmListener mListener;

    public MessageDialog(Context context, String message, ConfirmListener listener) {
        super(context);
        mContext = context;
        mMessage = message;
        mListener = listener;
        setDialogTheme();
        getWindow().setGravity(Gravity.CENTER);

        initDialog();
    }

    public MessageDialog textGravity(int gravity) {
        switch (gravity) {
            case GRAVITY_CENTER:
                mMessageTxt.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_LEFT:
                mMessageTxt.setGravity(Gravity.LEFT);
                break;
            case GRAVITY_RIGHT:
                mMessageTxt.setGravity(Gravity.RIGHT);
                break;
            default:
                break;
        }
        return this;
    }

    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
        this.setCanceledOnTouchOutside(true);// 点击外围区域，dialog不可取消
    }

    private void initDialog() {
        setContentView(R.layout.dialog_message);
        mTitleTxt = (TextView) findViewById(R.id.title_txt);
        mCancelTxt = (TextView) findViewById(R.id.cancel_txt);
        mConfirmTxt = (TextView) findViewById(R.id.confirm_txt);
        mMessageTxt = (TextView) findViewById(R.id.message_txt);
        mMessageTxt.setText(mMessage);
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

    public MessageDialog title(String title) {
        mTitleTxt.setText(title);
        return this;
    }

    public MessageDialog title(int resId) {
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