package com.prj.server.user;

import com.prj.entity.User;

import java.util.List;

public interface UserServer {

    public User login(User user);


    //添加用户
    public int addUser(User user);
}
