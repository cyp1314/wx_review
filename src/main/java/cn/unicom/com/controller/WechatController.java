package cn.unicom.com.controller;/**
 * Created by sh-wangbs on 2019/2/1.
 *
 * @Author sh-wangbs
 * @date 2019/2/1
 */

import cn.unicom.com.config.WeChatConfig;
import cn.unicom.com.config.jwt.Audience;
import cn.unicom.com.domain.User;
import cn.unicom.com.domain.VideoOrder;
import cn.unicom.com.service.UserService;
import cn.unicom.com.service.VideoOrderService;
import cn.unicom.com.utils.JsonData;
import cn.unicom.com.utils.JwtUtils;
import cn.unicom.com.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

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
    @Autowired
    private VideoOrderService videoOrderService;
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
    /***
     * @Description: WX支付回调函数
     * @Param: [request, response] 
     * @return: void 
     * @author: wangbs
     * @create: 2019/2/13 16:03
     */
    
    @RequestMapping("order/callback2")
    public void  callback2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        //换成高效流
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        //读取
        StringBuffer sb=new StringBuffer();
        String line="";
        while(((line=br.readLine()))!=null){
            sb.append(line);
        }
        //关流
        br.close();
        inputStream.close();
        Map<String, String> xmlToMap = WXPayUtil.xmlToMap(sb.toString());
        //把map 变成sortmap   进行签名验证
        SortedMap<String, String> sortedMap = WXPayUtil.SortedMap(xmlToMap);
        System.out.println("--------------------");
        System.out.println(sortedMap);
        boolean correctSign = WXPayUtil.isCorrectSign(sortedMap, weChatConfig.getKey());
        if (correctSign){
            if("SUCCESS".equals(sortedMap.get("return_code"))){
                //查询下单号
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder videoOrder= videoOrderService.findOrderByOutTradeNo(outTradeNo);
                //更新订单状态
                if (videoOrder!=null&&videoOrder.getState()==0){
                    VideoOrder videoOrder1=new VideoOrder();
                    videoOrder1.setOpenid(videoOrder.getOpenid());
                    videoOrder1.setOutTradeNo(videoOrder.getOutTradeNo());
                    videoOrder1.setState(1);
                    videoOrder1.setNotifyTime(new Date());
                    videoOrderService.updateOrderState(videoOrder1);
                    //通知微信修改成功
                    response.setContentType("text/xml");
                    response.getWriter().println("success");
                    return;
                }
            }
        }
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }
}
