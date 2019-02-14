package cn.unicom.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"cn.unicom.com.mapper"})
//开启事务管理
@EnableTransactionManagement
public class WxReviewApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WxReviewApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WxReviewApplication.class);
	}
}

