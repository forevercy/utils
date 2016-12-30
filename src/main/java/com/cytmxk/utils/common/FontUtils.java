package com.cytmxk.utils.common;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by chenyang on 2016/7/7.
 */
public class FontUtils {

    private static final String TAG = FontUtils.class.getCanonicalName();

    /**
     * 点阵字库的类型，默认为16*16
     */
    public enum DotMatrixFontType {
        TWELVE_TYPE(12), SIXTEEN_TYPE(16);

        private int mDotMatrixFontType;
        private DotMatrixFontType(int dotMatrixFontType) {
            this.mDotMatrixFontType = dotMatrixFontType;
        }

        public int getValue() {
            return this.mDotMatrixFontType;
        }

        @Override
        public String toString() {
            return String.valueOf(mDotMatrixFontType);
        }
    }
    private DotMatrixFontType mDotMatrixFontType = DotMatrixFontType.SIXTEEN_TYPE;

    /**
     * 字库名 默认HZK16
     */
    private static final String DOT_MATRIX_FONT = "HZK16";
    /**
     * 汉字的编码 默认GB2312
     */
    private static final String ENCODE = "GB2312";

    /**
	 * 一个字用点表示需要多少字节，16*16的字体需要32个字节
	 */
    private int mWordByteByDots;

    /**
     * 获取汉字字符串的点阵矩阵
     * @param text
     * @return
     */
    public boolean[][] getWordsMatrix(Context context, String text) {
        return getWordsMatrix(context, text, null);
    }

    public boolean[][] getWordsMatrix(Context context, String text, DotMatrixFontType dotMatrixFontType) {
        if (null == dotMatrixFontType) {
            this.mDotMatrixFontType = DotMatrixFontType.SIXTEEN_TYPE;
            this.mWordByteByDots = DotMatrixFontType.SIXTEEN_TYPE.getValue() * DotMatrixFontType.SIXTEEN_TYPE.getValue() / 8;
        } else {
            this.mDotMatrixFontType = dotMatrixFontType;
            this.mWordByteByDots = dotMatrixFontType.getValue() * dotMatrixFontType.getValue() / 8;
        }

        byte[] bytes = null;
        try {
            // 获取汉字文本的字节编码
            bytes = text.getBytes(ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 获取每个字节对应正数编码，即得到汉字对应的区码和位码
        int[] code = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            code[i] = bytes[i] < 0 ? 256 + bytes[i] : bytes[i];
        }

        int wordNumber = code.length / 2;
        boolean[][] wordsMatrix = new boolean[mDotMatrixFontType.getValue()][mDotMatrixFontType.getValue() * wordNumber];
        for (int i = 0; i < wordNumber; i++) {
            // 通过区码和位码获取字库中对应的字模信息
            byte[] temp = read(context, code[2 * i], code[2 * i + 1]);
            for (int j = 0; j < mWordByteByDots; j++) {
                for (int k = 0; k < 8; k++) {
                    // 将字模信息转化为Boolean类型的二维数组并且进行纵向填充数组
                    int row = (j * 8 + k) / 16 + i * mDotMatrixFontType.getValue();
                    int col = (j * 8 + k) % 16;
                    if (((temp[j] >> (7 - k)) & 1) == 1) {
                        wordsMatrix[col][row] = true;
                    } else {
                        wordsMatrix[col][row] = false;
                    }
                }
            }
        }

        return wordsMatrix;
    }

    /**
     * 从字库中获取指定区码和位码汉字的字模信息
     * @param areaCode 区码，对应编码的第一个字节
     * @param posCode  位码，对应编码的第二个字节
     * @return
     */
    private byte[] read(Context context, int areaCode, int posCode) {
        byte[] data = null;
        try {
            int area = areaCode - 0xa0;
            int pos = posCode - 0xa0;
            InputStream in = context.getAssets().open(DOT_MATRIX_FONT);
            int offset = ((area - 1) * 94 + pos -1) * mWordByteByDots;
            in.skip(offset);
            data = new byte[mWordByteByDots];
            in.read(data, 0, mWordByteByDots);
            in.close();
        } catch (IOException e) {
            Log.d(TAG, "IOException e = " + e.getMessage());
        }
        return data;
    }

    public static int countWordNumber(String text) throws UnsupportedEncodingException {
        return text.getBytes(ENCODE).length / 2;
    }

}
