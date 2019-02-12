package cn.unicom.com;

import cn.unicom.com.domain.User;
import cn.unicom.com.domain.Video;
import cn.unicom.com.mapper.UserMapper;
import cn.unicom.com.mapper.VideoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxReviewApplicationTests {
	@Autowired
	VideoMapper videoMapper;
	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {
		Video video = videoMapper.fingById(1);
		assertNotNull(video);
		User user = userMapper.fingById(1);
		assertNotNull(user);

	}

}

