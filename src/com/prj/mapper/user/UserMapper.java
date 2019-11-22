package com.prj.mapper.user;

import com.prj.entity.Classes;
import com.prj.entity.User;
import java.util.List;

public interface UserMapper {
    //查用户
    public List<User> queryUser(String uname,String pwd);
    //查班
    public List<Classes> queryClasses(Classes classes);

}
