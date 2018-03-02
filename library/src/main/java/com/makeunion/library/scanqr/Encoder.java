package com.makeunion.library.scanqr;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author :   Yoojia.Chen (yoojia.chen@gmail.com)
 * @since 1.0
 * 将文本信息生成QRCode图片
 */
public final class Encoder {

    public static final String TAG = Decoder.class.getSimpleName();

    private final EncoderBuilder mConfigEncoderBuilder;
    private final MultiFormatWriter mMultiFormatWriter;

    private Encoder(EncoderBuilder configEncoderBuilder) {
        mConfigEncoderBuilder = configEncoderBuilder;
        mMultiFormatWriter = new MultiFormatWriter();
    }

    /**
     * 将文本信息生成二维码图片
     *
     * @param content 文本内容
     * @return Bitmap对象，如果生成失败，返回null。
     */
    public Bitmap encodeQR(final String content) {
        return encode(content, mConfigEncoderBuilder.mOutputBitmapWidth, mConfigEncoderBuilder.mOutputBitmapHeight);
    }

    /**
     * 将文本信息生成二维码图片 并在二维码中央 生成制定图片（圆形）
     *
     * @param content           文本内容
     * @param pic               头像
     * @param defaultPic        默认头像
     * @param portraitSize      中央头像大小
     * @param circleBgColor     图片背景颜色(不需要则设0)
     * @param circleStrokeColor 图片最外环颜色(不需要则设0)
     * @param mCircleR          背景
     * @return Bitmap对象，如果生成失败，返回null。
     */
    public Bitmap encodeQRWithPic(final String content, Bitmap pic, Bitmap defaultPic, int portraitSize, int circleBgColor, int circleStrokeColor, int mCircleR) {
        return drawPic(encode(content, mConfigEncoderBuilder.mOutputBitmapWidth, mConfigEncoderBuilder.mOutputBitmapHeight),
                pic, defaultPic, portraitSize, circleBgColor, circleStrokeColor, mCircleR);
    }

    /**
     * 将文本信息生成二维码图片，指定输出图片宽度和高度
     *
     * @param content 文本内容
     * @param width   输出图片宽度
     * @param height  输出图片高度
     * @return Bitmap对象，如果生成失败，返回null。
     */
    public Bitmap encode(final String content, int width, int height) {
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("QRCode encode content CANNOT be empty");
        }
        final long start = System.currentTimeMillis();
        final Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        // 字符编码
        hints.put(EncodeHintType.CHARACTER_SET, mConfigEncoderBuilder.mCharset);
        // 设置二维码生成的容错率H为最高级别（容错率越高，像素点越多）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        if (mConfigEncoderBuilder.mHintMargin >= 0) {
            // 输出图片外边距
            hints.put(EncodeHintType.MARGIN, mConfigEncoderBuilder.mHintMargin);
        }
        BitMatrix result;
        try {
            result = mMultiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (Exception e) {
            Log.w(TAG, e);
            return null;
        }
        int finalBitmapWidth = result.getWidth();
        int finalBitmapHeight = result.getHeight();
        final int[] pixels = new int[finalBitmapWidth * finalBitmapHeight];
        for (int y = 0; y < finalBitmapHeight; y++) {
            int offset = y * finalBitmapWidth;
            for (int x = 0; x < finalBitmapWidth; x++) {
                pixels[offset + x] = result.get(x, y) ? mConfigEncoderBuilder.mCodeColor : mConfigEncoderBuilder.mBackgroundColor;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(finalBitmapWidth, finalBitmapHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, finalBitmapWidth, 0, 0, finalBitmapWidth, finalBitmapHeight);
        final long end = System.currentTimeMillis();
        Log.d(TAG, "QRCode encode in " + (end - start) + "ms");
        return bitmap;
    }

    /**
     * 在二维码中央 生成制定图片（圆形）
     *
     * @param qrBitmap          二维码Bitmap
     * @param pic               头像
     * @param defaultPic        默认头像
     * @param portraitSize      中央头像大小
     * @param circleBgColor     图片背景颜色(不需要则设0)
     * @param circleStrokeColor 图片最外环颜色(不需要则设0)
     * @param mCircleR          背景圆半径（若上述两个颜色都为0，则该属性无用）
     * @return Bitmap对象，如果生成失败，返回null。
     */
    public Bitmap drawPic(Bitmap qrBitmap, Bitmap pic, Bitmap defaultPic, int portraitSize, int circleBgColor, int circleStrokeColor, int mCircleR) {
        // 头像背景画笔
        Paint mBgCirclePaint;
        mBgCirclePaint = new Paint();
        mBgCirclePaint.setAntiAlias(true);
        mBgCirclePaint.setStyle(Paint.Style.FILL);
        mBgCirclePaint.setColor(circleBgColor);
        // 头像环画笔
        Paint mStrokeCirclePaint;
        mStrokeCirclePaint = new Paint();
        mStrokeCirclePaint.setAntiAlias(true);
        mStrokeCirclePaint.setStyle(Paint.Style.STROKE);
        mStrokeCirclePaint.setColor(circleStrokeColor);
        mStrokeCirclePaint.setStrokeWidth((float) 2.0);

        Bitmap tmp = pic != null ? pic : defaultPic;

        // 将头像变成圆形
        tmp = BitmapHelper.toRoundBitmap(tmp);

        // 对原有图片压缩显示大小
        Matrix mMatrix = new Matrix();
        float width = tmp.getWidth();
        float height = tmp.getHeight();
        mMatrix.setScale(portraitSize / width, portraitSize / height);
        tmp = Bitmap.createBitmap(tmp, 0, 0, (int) width,
                (int) height, mMatrix, true);

        // 头像图片的大小
        int portrait_W = tmp.getWidth();
        int portrait_H = tmp.getHeight();

        // 设置头像要显示的位置，即居中显示
        int left = (mConfigEncoderBuilder.mOutputBitmapWidth - portrait_W) / 2;
        int top = (mConfigEncoderBuilder.mOutputBitmapHeight - portrait_H) / 2;
        int right = left + portrait_W;
        int bottom = top + portrait_H;


        Rect rect1 = new Rect(left, top, right, bottom);
        // 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
        Canvas canvas = new Canvas(qrBitmap);
        // 画背景圆
        if (circleBgColor != 0) {
            canvas.drawCircle((left + right) / 2, (top + bottom) / 2, mCircleR, mBgCirclePaint);
        }
        if (circleStrokeColor != 0) {
            canvas.drawCircle((left + right) / 2, (top + bottom) / 2, mCircleR, mStrokeCirclePaint);
        }
        // 设置我们要绘制的范围大小，也就是头像的大小范围
        Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
        // 开始绘制
        canvas.drawBitmap(tmp, rect2, rect1, null);
        tmp.recycle();
        return qrBitmap;
    }


    public static class EncoderBuilder {

        private int mBackgroundColor = 0xFFFFFFFF;
        private int mCodeColor = 0xFF000000;
        private String mCharset = "UTF-8";
        private int mOutputBitmapWidth;
        private int mOutputBitmapHeight;
        private int mHintMargin = -1;

        /**
         * 设置生成二维码图片的背景色
         *
         * @param backgroundColor 背景色，如 0xFFFFFFFF
         * @return EncoderBuilder，用于链式调用
         */
        public EncoderBuilder setBackgroundColor(int backgroundColor) {
            mBackgroundColor = backgroundColor;
            return this;
        }

        /**
         * 设置二维码的编码块颜色
         *
         * @param codeColor 编码块颜色，如 0xFF000000
         * @return EncoderBuilder，用于链式调用
         */
        public EncoderBuilder setCodeColor(int codeColor) {
            mCodeColor = codeColor;
            return this;
        }

        /**
         * 设置文本编码格式
         *
         * @param charset 字符编码格式
         * @return EncoderBuilder，用于链式调用
         */
        public EncoderBuilder setCharset(String charset) {
            if (TextUtils.isEmpty(charset)) {
                throw new IllegalArgumentException("Illegal charset: " + charset);
            }
            mCharset = charset;
            return this;
        }

        /**
         * 设置输出图片的宽度
         *
         * @param outputBitmapWidth 宽度，单位：px
         * @return EncoderBuilder，用于链式调用
         */
        public EncoderBuilder setOutputBitmapWidth(int outputBitmapWidth) {
            mOutputBitmapWidth = outputBitmapWidth;
            return this;
        }

        /**
         * 设置输出图片的高度
         *
         * @param outputBitmapHeight 高度，单位：px
         * @return EncoderBuilder，用于链式调用
         */
        public EncoderBuilder setOutputBitmapHeight(int outputBitmapHeight) {
            mOutputBitmapHeight = outputBitmapHeight;
            return this;
        }

        /**
         * 设置输出二维码与图片边缘的距离
         *
         * @param hintMargin 距离值，正整数。
         */
        public EncoderBuilder setOutputBitmapPadding(int hintMargin) {
            mHintMargin = hintMargin;
            return this;
        }

        /**
         * @return QRCode生成器对象
         */
        public Encoder build() {
            return new Encoder(this);
        }


    }
}
