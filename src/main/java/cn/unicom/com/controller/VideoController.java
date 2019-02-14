package cn.unicom.com.controller;/**
 * Created by sh-wangbs on 2019/2/14.
 *
 * @Author sh-wangbs
 * @date 2019/2/14
 */

import cn.unicom.com.domain.Video;
import cn.unicom.com.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName VideoController
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/1413:47
 *@Version 1.0
 **/
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    VideoService videoService;

    /**
     * 分页接口
     * @param page 当前第几页，默认是第一页
     * @param size  每页显示几条
     * @return
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                            @RequestParam(value = "size",defaultValue = "10")int size){

        PageHelper.startPage(page,size);

        List<Video> list = videoService.findVoides();
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        Map<String,Object> data = new HashMap<>();
        data.put("total_size",pageInfo.getTotal());//总条数
        data.put("total_page",pageInfo.getPages());//总页数
        data.put("current_page",page);//当前页
        data.put("data",pageInfo.getList());//数据
        return data;
    }



}
