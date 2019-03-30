package payapp.best.com.f2fdemo;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

public class Alipay2DCode {

    /**
     *  生成二维码图片
     */
    public static Bitmap generateBitmap(String url) {

        Hashtable<EncodeHintType, Object> hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 二维码两边空白区域大小
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix matrix = null;
        try {
            matrix = (new MultiFormatWriter()).encode(url, BarcodeFormat.QR_CODE, 512, 512, hints);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];

            for(int y = 0; y < height; ++y) {
                for(int x = 0; x < width; ++x) {
                    pixels[y * width + x] = matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

}
