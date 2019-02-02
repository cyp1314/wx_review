package cn.unicom.com.service.impl;/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */

import cn.unicom.com.domain.User;
import cn.unicom.com.domain.Video;
import cn.unicom.com.mapper.UserMapper;
import cn.unicom.com.mapper.VideoMapper;
import cn.unicom.com.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@ClassName VideoServiceImpl
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/1/3113:57
 *@Version 1.0
 **/
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;


    @Override
    public List<Video> findVoides() {
        List<Video> voides = videoMapper.findVoides();

        return voides;
    }
}
