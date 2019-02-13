package cn.unicom.com.config;/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *@ClassName 读取微信配置文件中的参数
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/1/3115:00
 *@Version 1.0
 **/
@Configuration
@PropertySource(value = "classpath:application.properties")
@Data
public class WeChatConfig {

     //开放平台appid
    @Value("${wxopen.appid}")
    private String openAppid;
     //开放平台appsecret
    @Value("${wxopen.appsecret}")
    private String openAppsecret;
    //重定向第三方平台的地址
    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;

     //微信开放平台二维码连接(从微信官方文档拿的第一个连接)
    private final static String OPEN_QRCODE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
     // 开放平台获取access_token地址（从微信官方文档拿的第二个连接）
    private final static String OPEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
     //获取用户信息（从微信官方文档拿的第三个连接）
    private final static String OPEN_USER_INFO_URL ="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    public static String getOpenUserInfoUrl() {
        return OPEN_USER_INFO_URL;
    }
    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }

    public static String getOpenAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    // /wx xiadan

    /**
     * 公众号appid
     */
    @Value("${wxpay.appid}")
    private String appId;

    /**
     * 公众号秘钥
     */
    @Value("${wxpay.appsecret}")
    private String appsecret;

    /**
     * 商户号id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;


    /**
     * 支付key
     */
    @Value("${wxpay.key}")
    private String key;

    /**
     * 微信支付回调url
     */
    @Value("${wxpay.callback}")
    private String payCallbackUrl;


    /**
     * 统一下单url
     */
    private static final String UNIFIED_ORDER_URL = "http://api.xdclass.net/pay/unifiedorder";

    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER_URL;
    }

}