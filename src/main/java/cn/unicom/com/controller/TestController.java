package cn.unicom.com.controller;/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */

import cn.unicom.com.config.WeChatConfig;
import cn.unicom.com.domain.Video;
import cn.unicom.com.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName TestController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/1/319:01
 * @Version 1.0
 **/
@Controller
@RequestMapping("api/test")
public class TestController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private WeChatConfig weChatConfig;

    @GetMapping("findVideos")
    @ResponseBody
    public Object findVideos() {
       PageHelper.startPage(1,4);
        List<Video> voides = videoService.findVoides();
        PageInfo<Video> pageInfo =new PageInfo(voides);
        return pageInfo;
    }
}
