package cn.unicom.com.interceptor;/**
 * Created by sh-wangbs on 2019/2/2.
 *
 * @Author sh-wangbs
 * @date 2019/2/2
 */

import cn.unicom.com.config.jwt.Audience;
import cn.unicom.com.utils.JsonData;
import cn.unicom.com.utils.JwtUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *@ClassName LoginIntercepter
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/29:08
 *@Version 1.0
 **/
public class LoginIntercepter implements HandlerInterceptor {
    private static final Gson gson = new Gson();
    @Autowired
    private Audience audience;


    /***
     * @Description:
     * @Param: [request, response, handler] 
     * @return: boolean 
     * @author: wangbs
     * @create: 2019/2/2 9:09
     */
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        if (token !=null){
            //解析token
            Claims claims = JwtUtils.checkJWT(token, audience.getBase64Secret());
            if (claims!=null){
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                request.setAttribute("id",id);
                request.setAttribute("name",name);
                return  true;
            }

        }
        //给页面的提示信息
        sendJsonMessage(response, JsonData.buildError("请登录"));
        return false;
    }

    private void sendJsonMessage(HttpServletResponse response,Object message) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter=response.getWriter();
        printWriter.write(gson.toJson(message));
        printWriter.close();
        response.flushBuffer();
    }
}
