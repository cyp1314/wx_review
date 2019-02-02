package cn.unicom.com.utils;/**
 * Created by sh-wangbs on 2019/1/31.
 *
 * @Author sh-wangbs
 * @date 2019/1/31
 */

import cn.unicom.com.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 *@ClassName JwtUtils
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/1/3116:57
 *@Version 1.0
 **/
public class JwtUtils {
    public JwtUtils() {
    }
      /* 校验token
     * @param token
     * @return
             */
    public static Claims checkJWT(String token ,String base64Security){

        try{
            final Claims claims =  Jwts.parser().setSigningKey(base64Security).
                    parseClaimsJws(token).getBody();
            return  claims;

        }catch (Exception e){ }
        return null;

    }

 /***
  * @Description:
  * @Param: [user=>加密的用户, JWTname=》token前缀, expiresSecond =>过期时间, base64Secret=》加密秘钥]
  * @return: java.lang.String
  * @author: wangbs
  * @create: 2019/1/31 17:24
  */
    public static String geneJsonWebToken(User user,String JWTname,Long expiresSecond,String base64Secret){

        if(user == null || user.getId() == null || user.getName() == null
                || user.getHeadImg()==null){
            return null;
        }
        String token = Jwts.builder().setSubject(JWTname)
                .claim("id",user.getId())
                .claim("name",user.getName())
                .claim("img",user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiresSecond))
                .signWith(SignatureAlgorithm.HS256,base64Secret).compact();

        return token;
    }
}
