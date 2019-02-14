package cn.unicom.com.config;/**
 * Created by sh-wangbs on 2019/2/14.
 *
 * @Author sh-wangbs
 * @date 2019/2/14
 */

/**
 *@ClassName Cors
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/1413:41
 *@Version 1.0
 **/
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class Cors extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

}
