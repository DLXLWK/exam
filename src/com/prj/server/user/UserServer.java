package com.prj.server.user;

import com.prj.entity.Classes;
import com.prj.entity.User;

import java.util.List;

public interface UserServer {
    public List<User> queryUser(String uname,String pwd);
    //查班
    public List<Classes> queryClasses(Classes classes);

}
