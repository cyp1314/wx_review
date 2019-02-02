package cn.unicom.com.service.impl;/**
 * Created by sh-wangbs on 2019/2/1.
 *
 * @Author sh-wangbs
 * @date 2019/2/1
 */

import cn.unicom.com.config.WeChatConfig;
import cn.unicom.com.domain.User;
import cn.unicom.com.mapper.UserMapper;
import cn.unicom.com.service.UserService;
import cn.unicom.com.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 *@ClassName UserServiceImpl
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/113:38
 *@Version 1.0
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatConfig weChatConfig;
    @Override
    public User saveWeChatUser(String code) {
        //调用 微信的接口token
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),weChatConfig.getOpenAppid(),weChatConfig.getOpenAppsecret(),code);

        Map<String, Object> baseMap = HttpClientUtils.doGet(accessTokenUrl);
        //access_token  openid
        if(baseMap == null || baseMap.isEmpty()){ return  null; }
        String accessToken = (String)baseMap.get("access_token");
        String openId  = (String) baseMap.get("openid");
        //在这里用户已经登陆了，我们首先拿到用户的唯一标识openid  去我们的user表中查，该用户是否存在
        User userByOptionId = userMapper.findUserByOptionId(openId);
        //用户信息在我们数据库有了 直接放心
        if (userByOptionId!=null){
            return  userByOptionId;
        }
        //用户第一次通过扫一扫登录我们系统
        //通过第三次访问微信连接 获得 用户信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);
        //获取用户信息
        Map<String ,Object> baseUserMap =  HttpClientUtils.doGet(userInfoUrl);
        if(baseUserMap == null || baseUserMap.isEmpty()){ return  null; }
        String nickname = (String)baseUserMap.get("nickname");
        Double sexTemp  = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String province = (String)baseUserMap.get("province");
        String city = (String)baseUserMap.get("city");
        String country = (String)baseUserMap.get("country");
        String headimgurl = (String)baseUserMap.get("headimgurl");
        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress = sb.toString();
        try {
            //解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setOpenid(openId);
        user.setName(nickname);
        user.setSex(sex);
        user.setHeadImg(headimgurl);
        user.setCreateTime(new Date());
        user.setCity(finalAddress);
        userMapper.saveUser(user);


        return user;
    }
}
