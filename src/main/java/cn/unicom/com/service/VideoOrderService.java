package cn.unicom.com.service;

import cn.unicom.com.domain.VideoOrder;
import cn.unicom.com.dto.VideoOrderDto;

/**
 * Created by sh-wangbs on 2019/2/12.
 *
 * @Author sh-wangbs
 * @date 2019/2/12
 */
public interface VideoOrderService {
    String save(VideoOrderDto videoOrderDto) throws Exception;

    VideoOrder findOrderByOutTradeNo(String outTradeNo);

    void updateOrderState(VideoOrder videoOrder1);
}
