package cn.unicom.com.service.impl;/**
 * Created by sh-wangbs on 2019/2/12.
 *
 * @Author sh-wangbs
 * @date 2019/2/12
 */

import cn.unicom.com.domain.User;
import cn.unicom.com.domain.Video;
import cn.unicom.com.domain.VideoOrder;
import cn.unicom.com.dto.VideoOrderDto;
import cn.unicom.com.mapper.UserMapper;
import cn.unicom.com.mapper.VideoMapper;
import cn.unicom.com.mapper.VideoOrderMapper;
import cn.unicom.com.service.VideoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@ClassName VideoOrderServiceImpl
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/1214:11
 *@Version 1.0
 **/
@Service
public class VideoOrderServiceImpl implements VideoOrderService {
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public String save(VideoOrderDto videoOrderDto) {

       Video video= videoMapper.fingById(videoOrderDto.getId());

        User user=userMapper.fingById(videoOrderDto.getUserId());

        return null;
    }
}
