package cn.unicom.com.controller;

import cn.unicom.com.domain.VideoOrder;
import cn.unicom.com.dto.VideoOrderDto;
import cn.unicom.com.service.VideoOrderService;
import cn.unicom.com.utils.IpUtils;
import cn.unicom.com.utils.JsonData;
import cn.unicom.com.utils.QRCodeUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName OrderController
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/29:47
 *@Version 1.0
 **/
@Controller
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;
    @RequestMapping("save")
    @ResponseBody
    public void save(@RequestParam(value = "video_id",required = true)int videoId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        String ip = IpUtils.getIpAddr(request);
       int userId = (Integer) request.getAttribute("user_id");
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);
        //获取二维码连接
        String codeUrl = videoOrderService.save(videoOrderDto);
        if(codeUrl == null) {
            throw new  NullPointerException();
        }
        //生成二维码配置
        QRCodeUtil.encodeQRCode(codeUrl,400,400,"png",response);

    }
}
