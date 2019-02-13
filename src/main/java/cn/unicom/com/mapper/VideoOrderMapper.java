package cn.unicom.com.mapper;

import cn.unicom.com.domain.Video;
import cn.unicom.com.domain.VideoOrder;

public interface VideoOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoOrder record);

    int insertSelective(VideoOrder record);

    VideoOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoOrder record);

    int updateByPrimaryKey(VideoOrder record);
    //根据id 查询Video
    Video fingById(Integer id);
    //根据订单号查询订单
    VideoOrder findOrderByOutTradeNo(String outTradeNo);
    //更新订单状态
    void updateOrderState(VideoOrder videoOrder);
}