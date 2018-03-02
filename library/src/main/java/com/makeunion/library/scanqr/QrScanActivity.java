package com.makeunion.library.scanqr;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.makeunion.library.R;


public class QrScanActivity extends CaptureActivity {

    public static final String EXTRA_SCAN_RESULT = "scan_result";

    private ImageView mQrLineView;

    private TranslateAnimation mAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        RelativeLayout mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);
        int width = (int) (ScreenUtils.getScreenWidth(this) * 0.6667f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.capture);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        mCropLayout.addView(view, layoutParams);

        mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
        mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT,
                0.85f);
        mAnimation.setDuration(1500);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(new LinearInterpolator());
        mQrLineView.setAnimation(mAnimation);

        //设置SurfaceView
        setSurfaceView((SurfaceView) findViewById(R.id.capture_preview));
        //设置界面的整个相机预览界面的View 用于计算剪切图片的比率
        setContainerLayout((RelativeLayout) findViewById(R.id.capture_container));
        //设置剪切图片的预览View 用于计算剪切图片的比率
        setCropLayout(mCropLayout);
    }

    public void onBackClick(View view) {
        onBackPressed();
    }

    public void onLightClick(View view) {
        light();
    }

    public void onChooseImageClick(View view) {
        chosePic();
    }

    public void onStartScanClick(View view) {
        startScan();
        mQrLineView.startAnimation(mAnimation);
    }

    public void onStopScanClick(View view) {
        stopScan();
        mQrLineView.clearAnimation();
    }

    @Override
    public void handleFailForChosePic() {
        super.handleFailForChosePic();
        callbackFail();
    }

    @Override
    public void handleSuccessForChosePic(String result) {
        super.handleSuccessForChosePic(result);
        callbackSuccess(result);
    }

    @Override
    public void handleSuccessForScanPic(String result) {
        super.handleSuccessForScanPic(result);
        callbackSuccess(result);
    }

    private void callbackSuccess(String result) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCAN_RESULT, result);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    private void callbackFail() {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_stay, R.anim.side_bottom_out);
    }
}
