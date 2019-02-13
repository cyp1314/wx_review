package cn.unicom.com.controller;/**
 * Created by sh-wangbs on 2019/2/2.
 *
 * @Author sh-wangbs
 * @date 2019/2/2
 */

import cn.unicom.com.domain.VideoOrder;
import cn.unicom.com.dto.VideoOrderDto;
import cn.unicom.com.service.VideoOrderService;
import cn.unicom.com.utils.IpUtils;
import cn.unicom.com.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public JsonData save(@RequestParam(value = "video_id",required = true)int videoId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        String ip = IpUtils.getIpAddr(request);
        //int userId = request.getAttribute("user_id");
        int userId = 1;    //临时写死的配置
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


        return JsonData.buildSuccess("wxsuccess");
    }
}
