package com.makeunion.library.scanqr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.makeunion.library.R;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CaptureActivity extends AppCompatActivity implements Callback,
        EasyPermissions.PermissionCallbacks {
    private static final String TAG = "CaptureActivity";
    private static final int RC_CAMERA_PERM = 124;
    private static final long VIBRATE_DURATION = 200L;
    //闪光灯开关标识，默认关闭状态
    protected boolean mLightFlag = true;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.50f;
    private static final int REQ_CHOOSE = 1;
    private boolean vibrate;
    private int x = 0;
    private int y = 0;
    private int cropWidth = 0;
    private int cropHeight = 0;
    private boolean isNeedCapture = false;
    private CameraManager mCameraManager;
    private SurfaceView mSurfaceView;
    private ViewGroup mCropLayout;
    private ViewGroup mContainer;
    private ExecutorService mDecodeFromPicExecutor;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 CameraManager
        mCameraManager = CameraManager.newInstance(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera_tip),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    protected void light() {
        if (mLightFlag) {// 开闪光灯
            mLightFlag = false;
            mCameraManager.openLight();
        } else {// 关闪光灯
            mLightFlag = true;
            mCameraManager.offLight();
        }
    }

    public CameraManager getCameraManager() {
        return mCameraManager;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        startScan();
    }

    public void startScan() {
        if (mSurfaceView == null) {
            Log.e(TAG,
                    "initSurfaceView: mSurfaceView==null,please try to call method setSurfaceView"
                            + "()");
            return;
        }
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    public void stopScan() {
        if (handler != null) {
            handler.quitSynchronously();
            handler.onDestroy();
            handler = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mCameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    public void handleDecode(String result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (result.equals("")) {
            handler.sendEmptyMessage(CaptureActivityHandler.MSG_RESTART_PREVIEW);
        } else {
            handleSuccessForScanPic(result);
        }
    }

    /**
     * 二维码解析成功  扫码
     */
    public void handleSuccessForScanPic(String result) {

    }

    public void handleAlbumDecode(String result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (result.equals("")) {
            handleFailForChosePicRunOnUiThread();
        } else {
            handleSuccessForChosePicRunOnUiThread(result);
        }
    }

    /**
     * 二维码解析失败 选择图片
     */
    public void handleFailForChosePic() {

    }

    /**
     * 二维码解析失败 选择图片
     */
    private void handleFailForChosePicRunOnUiThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleFailForChosePic();
            }
        });
    }

    /**
     * 二维码解析成功  选择图片
     *
     * @param result 解析结果
     */
    public void handleSuccessForChosePic(String result) {

    }

    /**
     * 二维码解析成功  选择图片
     *
     * @param result 解析结果
     */
    private void handleSuccessForChosePicRunOnUiThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleSuccessForChosePic(result);
            }
        });
    }

    /**
     * 设置SurfaceView
     */
    protected void setSurfaceView(SurfaceView surfaceView) {
        this.mSurfaceView = surfaceView;
    }

    /**
     * 设置剪切图片的预览View 用于计算剪切图片的比率
     */
    protected void setCropLayout(ViewGroup cropLayout) {
        this.mCropLayout = cropLayout;
    }

    /**
     * 设置界面的整个相机预览界面的View 用于计算剪切图片的比率
     */
    protected void setContainerLayout(ViewGroup containerLayout) {
        this.mContainer = containerLayout;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                mCameraManager.openDriver(surfaceHolder);
                Point point = mCameraManager.getCameraResolution();
                int width = point.y;
                int height = point.x;
                int x = mCropLayout.getLeft() * width / mContainer.getWidth();
                int y = mCropLayout.getTop() * height / mContainer.getHeight();

                int cropWidth = mCropLayout.getWidth() * width / mContainer.getWidth();
                int cropHeight = mCropLayout.getHeight() * height / mContainer.getHeight();
                setX(x);
                setY(y);
                setCropWidth(cropWidth);
                setCropHeight(cropHeight);
                // 设置是否需要截图
                setNeedCapture(false);
            }
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            showAlertDialogAndFinish();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(CaptureActivity.this);
            handler.startDecodeQrCode();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public Handler getHandler() {
        return handler;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            try {
                setVolumeControlStream(AudioManager.STREAM_MUSIC);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(beepListener);

                AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);

                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                        file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (Exception e) {
                Log.e(TAG, "MediaPlayer has Exception，give up，finish and back !");
                mediaPlayer = null;
                finish();
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    protected void chosePic() {
        Intent innerIntent = new Intent(
                Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CHOOSE) {
            handleChoosePic(data);
        }
    }

    /**
     * 照片处理
     */
    private void handleChoosePic(Intent data) {
        if (data == null) {
            return;
        }
        if (mDecodeFromPicExecutor == null) {
            mDecodeFromPicExecutor = Executors.newCachedThreadPool();
        }
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                final String path = FileUtils.getBitmapPtah(this, uri);
                mDecodeFromPicExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        scanningImage(path);
                    }
                });
            }
        }
    }

    protected void scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0) {
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        if (scanBitmap != null) {
            RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
            QRCodeReader reader = new QRCodeReader();
            try {
                Result value = reader.decode(bitmap1, hints);
                if (value != null) {
                    handleAlbumDecode(value.getText());
                } else {
                    handleFailForChosePicRunOnUiThread();
                }
            } catch (Exception e) {
                handleFailForChosePicRunOnUiThread();
            }
        } else {
            handleFailForChosePicRunOnUiThread();
        }
    }

    public boolean isNeedCapture() {
        return isNeedCapture;
    }

    public void setNeedCapture(boolean isNeedCapture) {
        this.isNeedCapture = isNeedCapture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        this.finish();
    }

    /**
     * 6.0以下厂商定制系统关闭摄像头权限的错误提示
     */
    private void showAlertDialogAndFinish() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.easy_permissions_denied_camera_tip)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }
}