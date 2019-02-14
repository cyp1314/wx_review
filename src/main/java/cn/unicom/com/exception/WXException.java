package cn.unicom.com.exception;/**
 * Created by sh-wangbs on 2019/2/14.
 *
 * @Author sh-wangbs
 * @date 2019/2/14
 */

import lombok.Data;

/**
 *@ClassName WXException
 *@Description TODD
 *@AUTHOR sh-wangbs
 *@Date 2019/2/1410:31
 *@Version 1.0
 **/

@Data
public class WXException extends RuntimeException {
    private String data;
    private  String mag;

    public WXException() {
        super();
    }

    public WXException(String data, String mag) {
        super();
        this.data = data;
        this.mag = mag;
    }

}
