package com.prj.mapper.user;

import com.prj.entity.Exam;
import com.prj.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public List<User> queryUser(String uname);


}
