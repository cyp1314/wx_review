package cn.unicom.com.mapper;

import cn.unicom.com.domain.User;
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findUserByOptionId(String optionId);
    /***
     * @Description: 保存用户信息
     * @Param: [user] 
     * @return: void 
     * @author: wangbs
     * @create: 2019/2/1 14:40
     */
    void saveUser(User user);
}