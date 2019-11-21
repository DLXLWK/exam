package com.prj.server.user;

import com.prj.entity.User;
import com.prj.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserServerImpl")
public class UserServerImpl implements UserServer {
    @Autowired
    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public List<User> queryUser(String uname) {
        return userMapper.queryUser(uname);
    }
}
