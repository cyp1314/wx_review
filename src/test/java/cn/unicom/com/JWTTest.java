package cn.unicom.com;/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */

import cn.unicom.com.domain.User;
import cn.unicom.com.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;

/**
 *@ClassName JWTTest
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/1/3117:26
 *@Version 1.0
 **/
public class JWTTest {

    @Test
    public void testGeneJwt(){

        User user = new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("xd");

        String token = JwtUtils.geneJsonWebToken(user,"wbs",1000000L,"1234567hgrfedwasdfgh");
        System.out.println(token);

    }


    @Test
    public void testCheck(){

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YnMiLCJpZCI6OTk5LCJuYW1lIjoieGQiLCJpbWciOiJ3d3cueGRjbGFzcy5uZXQiLCJpYXQiOjE1NDg5MjY5NzQsImV4cCI6MTU0ODkyNzk3NH0.bgOGP1yUp0p2UFLigCcrbl9WQBs7Z_IkSRTyt_XAm5M";
        Claims claims = JwtUtils.checkJWT(token,"1234567hgrfedwasdfgh");
        if(claims != null){
            String name = (String)claims.get("name");
            String img = (String)claims.get("img");
            int id =(Integer) claims.get("id");
            System.out.println(name);
            System.out.println(img);
            System.out.println(id);
        }else{
            System.out.println("非法token");
        }


    }

    @Test
    public void testGeneJwt2(){

        User user = new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("xd");

        String token = JwtUtils.geneJsonWebToken(user,"wbs",1000000L,"1234567hgrfedwasdfgh");
        System.out.println(token);

    }
}
