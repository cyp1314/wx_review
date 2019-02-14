package cn.unicom.com.exception;
import cn.unicom.com.utils.JsonData;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 异常处理控制器
 */
@ControllerAdvice
@ResponseBody
public class WXExceptionHandel {
    @ExceptionHandler(Exception.class)
    public JsonData Handler(Exception e){
        if (e instanceof WXException){
            return  JsonData.buildError("自定义异常WXException",-1);
        }else {
           return  JsonData.buildError("系统异常 请联系后台用户");
        }
    }
}
