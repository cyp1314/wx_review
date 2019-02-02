package cn.unicom.com.service;

import cn.unicom.com.domain.User;

/**
 * Created by sh-wangbs on 2019/2/1.
 *
 * @Author sh-wangbs
 * @date 2019/2/1
 */
public interface UserService {
    /***
     * @Description: 用户通过扫一扫登录后，保存或者修改用户
     * @Param: []
     * @return: cn.unicom.com.domain.User
     * @author: wangbs
     * @create: 2019/2/1 13:36
     */

    User saveWeChatUser(String code);
}
