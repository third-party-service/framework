package com.jzg.framework.utils.code;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 条形码生成
 */
public final class BarCodeUtils {
    /**
     * START_GUARD
     */
    public static final int START_GUARD = 3;
    /**
     * END_GUARD
     */
    public static final int END_GUARD = 3;
    /**
     * MIDDLE_GUARD
     */
    public static final int MIDDLE_GUARD = 5;
    /**
     * LEFT_BAR_1
     */
    public static final int LEFT_BAR_1 = 7;
    /**
     * RIGTH_BAT_1
     */
    public static final int RIGTH_BAT_1 = 7;
    /**
     * LEFT_BAR_2
     */
    public static final int LEFT_BAR_2 = 6;
    /**
     * RIGTH_BAT_2
     */
    public static final int RIGTH_BAT_2 = 6;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(BarCodeUtils.class);
    /**
     * 默认编码格式
     */
    private static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * 默认宽度
     */
    private static final int DEFAULT_WIDTH = 200;
    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 200;
    /**
     * 默认图片格式
     */
    private static final String DEFAULT_FORMAT = "png";

    /**
     * 条形码格式
     */
    private static final BarcodeFormat BARCODE_FORMAT = BarcodeFormat.EAN_13;

    private BarCodeUtils() {
    }

    /**
     * 生成二维码
     *
     * @param content      要生成二维码内容
     * @param outputStream 输出流，例如 response.getOutputStream()
     * @param width        二维码图片宽度  默认200
     * @param height       二维码图片高度 默认200
     * @param format       图片格式 默认png
     * @throws java.lang.Exception 异常
     */
    public static void encode(String content, OutputStream outputStream, int width, int height, String format) throws Exception {
        Assert.notNull(format);
        Assert.notNull(width);
        Assert.notNull(height);


        int codeWidth = START_GUARD + // start guard
                (LEFT_BAR_1 * LEFT_BAR_2) + // left bars
                MIDDLE_GUARD + // middle guard
                (RIGTH_BAT_1 * RIGTH_BAT_2) + // right bars
                END_GUARD; // end guard
        codeWidth = Math.max(codeWidth, width);

        BitMatrix bitMatrix = getBitMatrix(content, codeWidth, height);

        MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
    }

    /**
     * 生成二维码，写入文件
     *
     * @param content  要生成二维码内容
     * @param filePath 文件路径
     * @param width    二维码图片宽度
     * @param height   二维码图片高度
     * @param format   图片格式
     * @throws java.lang.Exception 异常
     */
    public static void encode(String content, String filePath, int width, int height, String format) throws Exception {
        Assert.notNull(format);
        Assert.notNull(width);
        Assert.notNull(height);

        BitMatrix bitMatrix = getBitMatrix(content, width, height);


        File file = new File(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, format, file.toPath());

    }

    /**
     * 生成二维码，默认大小 200*200 png
     *
     * @param content      要生成二维码内容
     * @param outputStream 输出流，例如 response.getOutputStream()
     * @throws java.lang.Exception 异常
     */
    public static void encode(String content, OutputStream outputStream) throws Exception {
        encode(content, outputStream, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FORMAT);
    }

    /**
     * 生成二维码，默认大小 200*200
     *
     * @param content      要生成二维码内容
     * @param outputStream 输出流，例如 response.getOutputStream()
     * @param format       图片格式，例如png
     * @throws Exception 异常
     */
    public static void encode(String content, OutputStream outputStream, String format) throws Exception {
        encode(content, outputStream, DEFAULT_WIDTH, DEFAULT_HEIGHT, format);
    }

    /**
     * 生成二维码，默认大小 200*200  png
     *
     * @param content  要生成二维码内容
     * @param filePath 文件路径
     * @throws Exception 异常
     */
    public static void encode(String content, String filePath) throws Exception {
        encode(content, filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FORMAT);
    }

    /**
     * 生成二维码，默认大小 200*200
     *
     * @param content  要生成二维码内容
     * @param filePath 文件路径
     * @param format   图片格式，例如png
     * @throws Exception 异常
     */
    public static void encode(String content, String filePath, String format) throws Exception {
        encode(content, filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT, format);
    }


    /**
     * 扫描解码
     *
     * @param filePath 文件路径
     * @return String 解码后字符串
     * @throws Exception 异常
     */
    public static String decode(String filePath) throws Exception {
        BufferedImage image = null;
        Result result = null;

        image = ImageIO.read(new File(filePath));
        if (image == null) {
            System.out.println("the decode image may be not exit.");
        }
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        result = new MultiFormatReader().decode(bitmap, null);

        return result.getText();
    }

    /**
     * 获取BitMatrix对象
     *
     * @param content 内容
     * @param width   宽度
     * @param height  高度
     * @return BitMatrix
     * @throws WriterException 异常
     */
    private static BitMatrix getBitMatrix(String content, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        //编码
        hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODING);
        //纠错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BARCODE_FORMAT, width, height, hints);

        return bitMatrix;
    }
}
