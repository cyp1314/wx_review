package cn.unicom.com.service;

import cn.unicom.com.domain.User;
import cn.unicom.com.domain.Video;

import java.util.List;

/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */
public interface VideoService {
    /***
     * @Description: 查询所有的voides 
     * @Param: [] 
     * @return: java.util.List<cn.unicom.com.domain.Video> 
     * @author: wangbs
     * @create: 2019/1/31 14:24
     */
    
    List<Video> findVoides();

}
