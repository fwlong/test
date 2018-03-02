package com.makeunion.library.scanqr;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.PlanarYUVLuminanceSource;

import java.io.File;
import java.io.FileOutputStream;

import cn.memedai.scanqrlib.ZBarDecoder;

/**
 * 描述: 接受消息后解码
 */
final class DecodeHandler extends Handler {

    CaptureActivity activity = null;

    DecodeHandler(CaptureActivity activity, Looper looper) {
        super(looper);
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case CaptureActivityHandler.MSG_DECODE:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case CaptureActivityHandler.MSG_DECODE_ALBUM:
                decodeAlbum((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case CaptureActivityHandler.MSG_QUIT:
                getLooper().quit();
                break;
        }
    }

    private void decodeAlbum(byte[] data, int width, int height) {
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;

        if (activity != null) {
            ZBarDecoder manager = new ZBarDecoder();
            String result = manager.decodeCrop(rotatedData, width, height, 0, 0, width,
                    height);
            if (result != null) {
                if (null != activity.getHandler()) {
                    Message msg = Message.obtain();
                    msg.obj = result;
                    msg.what = CaptureActivityHandler.MSG_DECODE_SUCCESS;
                    activity.getHandler().sendMessage(msg);
                }
            } else {
                if (null != activity.getHandler()) {
                    activity.getHandler().sendEmptyMessage(
                            CaptureActivityHandler.MSG_DECODE_ALBUM_FAIL);
                }
            }
        }
    }

    private void decode(byte[] data, int width, int height) {
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;
        ZBarDecoder manager = new ZBarDecoder();
        if (activity != null) {
            String result = manager.decodeCrop(rotatedData, width, height, activity.getX(),
                    activity.getY(), activity.getCropWidth(),
                    activity.getCropHeight());

            if (result != null && activity != null) {
                if (activity.isNeedCapture()) {
                    // 生成bitmap
                    PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(rotatedData,
                            width, height, activity.getX(), activity.getY(),
                            activity.getCropWidth(), activity.getCropHeight(), false);
                    int[] pixels = source.renderThumbnail();
                    int w = source.getThumbnailWidth();
                    int h = source.getThumbnailHeight();
                    Bitmap bitmap = Bitmap.createBitmap(pixels, 0, w, w, h,
                            Bitmap.Config.ARGB_8888);
                    try {
                        String rootPath =
                                Environment.getExternalStorageDirectory().getAbsolutePath()
                                        + "/Qrcode/";
                        File root = new File(rootPath);
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File f = new File(rootPath + "Qrcode.jpg");
                        if (f.exists()) {
                            f.delete();
                        }
                        f.createNewFile();

                        FileOutputStream out = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (activity != null && null != activity.getHandler()) {
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = CaptureActivityHandler.MSG_DECODE_SUCCESS;
                    activity.getHandler().sendMessage(msg);
                }
            } else {
                if (activity != null && null != activity.getHandler()) {
                    activity.getHandler().sendEmptyMessage(CaptureActivityHandler.MSG_DECODE_FAIL);
                }
            }
        }
    }

    public void onDestroy() {
        if (activity != null) {
            getLooper().quit();
            activity = null;
        }
        removeCallbacksAndMessages(null);

    }

}
