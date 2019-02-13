package cn.unicom.com.utils;/**
 * Created by sh-wangbs on 2019/2/13.
 *
 * @Author sh-wangbs
 * @date 2019/2/13
 */

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName QRCodeUtil
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/1313:18
 *@Version 1.0
 **/
public class QRCodeUtil {
    private static Logger log = LoggerFactory.getLogger(QRCodeUtil.class);
    /**
     * 生成二维码
     * @param text 内容，可以是链接或者文本
     * @param path 生成的二维码位置
     * @param width 宽度，默认300
     * @param height 高度，默认300
     * @param format 生成的二维码格式，默认png
     */
    public static void encodeQRCode(String text, Integer width, Integer height, String format, HttpServletResponse response) {
        try {

        /*    // 得到文件对象
            File file = new File(path);
            // 判断目标文件所在的目录是否存在
            if(!file.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建父目录
                log.info("目标文件所在目录不存在，准备创建它！");
                if(!file.getParentFile().mkdirs()) {
                    log.info("创建目标文件所在目录失败！");
                    return;
                }
            }*/
            Map<EncodeHintType, Object> hints = new HashMap();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            // 设置字符集编码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 生成二维码矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
           /* // 二维码路径
            Path outputPath = Paths.get(path);*/
            OutputStream outputPath =  response.getOutputStream();
            // 写入文件
            MatrixToImageWriter.writeToStream(bitMatrix, format,  outputPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 对二维码图片进行解码
     * @param filePath 二维码路径
     * @return 解码后对内容
     */
   /* public static JSONObject decodeQRCode(String filePath) {

        try {

            // 读取图片
            BufferedImage image = ImageIO.read(new File(filePath));

            // 多步解析
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            // 一步到位
            // BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)))

            // 设置字符集编码
            Map<DecodeHintType, String> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            // 对图像进行解码
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            // 解码内容
            JSONObject content = JSONObject.parseObject(result.getText());

            System.out.println("图片内容：  ");
            System.out.println("content： " + content.toJSONString());
            System.out.println("图片中格式：  ");
            System.out.println("encode： " + result.getBarcodeFormat());

            return content;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }*/
}
