package com.nowcoder.humanresources.dao;

import com.nowcoder.humanresources.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectByName(String name);

    int insertUser(User user);
    int updatePassword(String username,String password);

    void updateUser(User user);

}
