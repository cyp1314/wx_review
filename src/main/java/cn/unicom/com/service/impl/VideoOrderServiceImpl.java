package cn.unicom.com.service.impl;/**
 * Created by sh-wangbs on 2019/2/12.
 *
 * @Author sh-wangbs
 * @date 2019/2/12
 */

import cn.unicom.com.config.WeChatConfig;
import cn.unicom.com.domain.User;
import cn.unicom.com.domain.Video;
import cn.unicom.com.domain.VideoOrder;
import cn.unicom.com.dto.VideoOrderDto;
import cn.unicom.com.mapper.UserMapper;
import cn.unicom.com.mapper.VideoMapper;
import cn.unicom.com.mapper.VideoOrderMapper;
import cn.unicom.com.service.VideoOrderService;
import cn.unicom.com.utils.CommonUtils;
import cn.unicom.com.utils.HttpClientUtils;
import cn.unicom.com.utils.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *@ClassName VideoOrderServiceImpl
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/1214:11
 *@Version 1.0
 **/
@Service
public class VideoOrderServiceImpl implements VideoOrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //用于统计使用，一般项目小项目不用可忽略
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    VideoOrderMapper videoOrderMapper;
    @Autowired
    WeChatConfig weChatConfig;
    @Transactional()
    @Override
    public String save(VideoOrderDto videoOrderDto) throws Exception {
        //这个数据会保存到统计信息的日志文件中去。
        dataLogger.info("module=video_order`api=save`user_id={}`video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());
        //正常的日志记录
        logger.info("module=video_order`api=save`user_id={}`video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());
        Video video= videoMapper.fingById(videoOrderDto.getVideoId());
       User user=userMapper.fingById(videoOrderDto.getUserId());
        //生成dd
        VideoOrder videoOrder =new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setNickname(user.getName());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setOpenid(user.getOpenid());
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());
        videoOrder.setState(0);
        videoOrder.setCreateTime(new Date());
        videoOrder.setUserId(videoOrderDto.getUserId());
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setDel(0);
        videoOrderMapper.insert(videoOrder);
        //统一下单
        //获取codeurl
        String codeUrl = unifiedOrder(videoOrder);
//       int i= 1/0;

        return codeUrl;
    }
    /***
     * @Description: 根据订单号 查询订单
     * @Param: [outTradeNo] 
     * @return: cn.unicom.com.domain.VideoOrder 
     * @author: wangbs
     * @create: 2019/2/13 13:54
     */
    
    @Override
    public VideoOrder findOrderByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findOrderByOutTradeNo(outTradeNo);
    }
    /***
     * @Description: 更新订单状态 
     * @Param: []
     * @return: void 
     * @author: wangbs
     * @create: 2019/2/13 14:02
     */
    
    @Override
    public void updateOrderState(VideoOrder videoOrder) {
         videoOrderMapper.updateOrderState(videoOrder);
        
    }

    private String unifiedOrder(VideoOrder videoOrder) throws Exception {
        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id",weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");
        //获取sign
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);
        //map转xml
        String payXml = WXPayUtil.mapToXml(params);
        //统一下单
        String orderStr = HttpClientUtils.doPost(WeChatConfig.getUnifiedOrderUrl(),payXml,4000);
        if(null == orderStr) {
            return null;
        }
        Map<String, String> stringStringMap = WXPayUtil.xmlToMap(orderStr);
        if (stringStringMap!=null){
            return  stringStringMap.get("code_url");
        }

        return null;
    }
}
