package cn.unicom.com.config;/**
 * Created by sh-wangbs on 2019/2/2.
 *
 * @Author sh-wangbs
 * @date 2019/2/2
 */

import cn.unicom.com.interceptor.LoginIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *@ClassName IntercepterConfig
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/29:27
 *@Version 1.0
 **/
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new LoginIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //可以添加多个
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/www/api/order/**");
    }
}
