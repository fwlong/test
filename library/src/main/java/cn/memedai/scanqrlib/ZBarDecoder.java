package cn.memedai.scanqrlib;


public class ZBarDecoder {

    static {
        System.loadLibrary("ZBarDecoder");
    }

    public native String decodeRaw(byte[] data, int width, int height);

    public native String decodeCrop(byte[] data, int width, int height, int x, int y, int cwidth, int cheight);
}
