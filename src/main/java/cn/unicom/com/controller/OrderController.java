package cn.unicom.com.controller;/**
 * Created by sh-wangbs on 2019/2/2.
 *
 * @Author sh-wangbs
 * @date 2019/2/2
 */

import cn.unicom.com.utils.JsonData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *@ClassName OrderController
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/29:47
 *@Version 1.0
 **/
@Controller
@RequestMapping("api/order")
public class OrderController {
    @RequestMapping("test")
    @ResponseBody
    public JsonData orderTest(){
        return JsonData.buildSuccess("下单成功");
    }
}
