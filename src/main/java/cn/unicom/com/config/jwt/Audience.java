package cn.unicom.com.config.jwt;/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *@ClassName Audience
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/1/3116:45
 *@Version 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "audience")
@Data
public class Audience {
    private String clientId;
    //签名秘钥
    private String base64Secret;

    private String name;
    //token过期时间
    private Long expiresSecond;
    //token 存在 header 中的参数
    private String header;
}

