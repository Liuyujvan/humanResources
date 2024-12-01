package com.nowcoder.humanresources.service;

import com.nowcoder.humanresources.dao.UserMapper;
import com.nowcoder.humanresources.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserByName(String name){
        return userMapper.selectByName(name);
    }
    public int updatePassword(String username,String password){


        return   userMapper.updatePassword(username,password);
    }
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    // 检查旧密码是否正确
    public boolean checkPassword(User user, String oldPassword) {
        return user.getPassword().equals(oldPassword); // 简单字符串比较，实际应用中可考虑加密
    }
    public Map<String,Object> login(String username, String password){
        Map<String,Object> map = new HashMap<>();
        //空值处理
        if (StringUtils.isBlank(username)){
            map.put("error","账号不能为空！");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("error","密码不能为空！");
            return map;
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if (user==null){
            map.put("error","该账号不存在！");
            return map;
        }


        //验证密码
      //  password = CommunityUtil.md5(password+user.getSalt());
        if (!user.getPassword().equals(password)){
            map.put("error","密码不正确！");
            return map;
        }
        //登录成功凭证
//        LoginTicket loginTicket = new LoginTicket();
//
//        loginTicket.setUserId(user.getId());
//        loginTicket.setTicket(CommunityUtil.genertateUUID());
//        loginTicket.setStatus(0);
//        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredSeconds*1000));
//        loginTicketMapper.insertLoginTicket(loginTicket);
//        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
//        redisTemplate.opsForValue().set(redisKey, loginTicket);
        map.put("user",user);

        return map;
    }

    public Map<String,Object> register(String username,String password,int type){
        HashMap<String,Object> map = new HashMap<>();
        User user = userMapper.selectByName(username);
        if (StringUtils.isBlank(username)){
            map.put("error","账号不能为空！");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("error","密码不能为空！");
            return map;
        }
        if(user!=null){
            map.put("error","该用户名已存在，请重新输入！");
            return map;
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setType(type);
        int result = userMapper.insertUser(user);
        map.put("user",user);
        return map;
    }
}
