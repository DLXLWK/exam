package com.prj.server.user;

import com.prj.entity.User;

import java.util.List;

public interface UserServer {
    public List<User> queryUser(String uname);


}
