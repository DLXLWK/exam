package com.prj.mapper.user;

import com.prj.entity.User;


public interface UserMapper {
    public User login(User user);

    //添加用户
    public int addUser(User user);

}
