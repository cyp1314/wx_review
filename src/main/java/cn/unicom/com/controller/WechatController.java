package cn.unicom.com.controller;/**
 * Created by sh-wangbs on 2019/2/1.
 *
 * @Author sh-wangbs
 * @date 2019/2/1
 */

import cn.unicom.com.config.WeChatConfig;
import cn.unicom.com.config.jwt.Audience;
import cn.unicom.com.domain.User;
import cn.unicom.com.service.UserService;
import cn.unicom.com.utils.JsonData;
import cn.unicom.com.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *@ClassName WechatController
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/18:49
 *@Version 1.0
 **/
@Controller
@RequestMapping("api/wx")
public class WechatController {
    @Autowired
    private Audience audience;
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserService userService;
    /***
     * @Description: 用户生成用户能扫一扫的微信二维码
     * @Param: [accessPage] 
     * @return: cn.unicom.com.utils.JsonData 
     * @author: wangbs
     * @create: 2019/2/1 8:52
     */
    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {
        //获取跳转到我们自己项目的重定向地址
        String redirectUrl = weChatConfig.getOpenRedirectUrl();
        String callbackUrl = URLEncoder.encode(redirectUrl,"GBK"); //进行编码
        String qrcodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(), weChatConfig.getOpenAppid(), callbackUrl, accessPage);
        return JsonData.buildSuccess(qrcodeUrl);
    }
    /***
     * @Description:  我们自己网站用于回调的借口
     * @Param: [code=>用户的唯一标识, state=》我们自己设定带的参数（比较带用户当前扫码的地址）, response]
     * @return: cn.unicom.com.utils.JsonData 
     * @author: wangbs
     * @create: 2019/2/1 14:20
     */
    @GetMapping("callback")
    @ResponseBody
    public JsonData  callback(@RequestParam(value = "code",required = true) String code,
                              String state, HttpServletResponse response) throws IOException {
        //保存用户 微信一键登录的（剩下的两次交互写在了service）
        User user = userService.saveWeChatUser(code);
        //判断用户是否成功登陆 不为null 用户登录成功，做你该做的业务，为null 用户没登录。
     /*   if(user != null){
            //生成jwt
            String token = JwtUtils.geneJsonWebToken(user);
            // state 当前用户的页面地址，需要拼接 http://  这样才不会站内跳转
            response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));
        }*/

     if(user!=null){
         //通过jwt生成token给前端
         String token=JwtUtils.geneJsonWebToken(user,audience.getName(),audience.getExpiresSecond(),audience.getBase64Secret());
         response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));
     }

        return null ;
    }
}
