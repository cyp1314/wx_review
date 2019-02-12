package cn.unicom.com.mapper;

import cn.unicom.com.domain.User;
import cn.unicom.com.domain.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
    //查询所有的video
    List<Video> findVoides();
    //根据id 查询video
    Video fingById(Integer id);
}