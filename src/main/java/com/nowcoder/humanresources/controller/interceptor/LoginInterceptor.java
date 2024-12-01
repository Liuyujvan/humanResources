package com.nowcoder.humanresources.controller.interceptor;

import com.nowcoder.humanresources.entity.User;
import com.nowcoder.humanresources.service.UserService;
import com.nowcoder.humanresources.util.CookieUtil;
import com.nowcoder.humanresources.util.HostHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取凭证
        String ticket = CookieUtil.getValue(request,"user");
        if (ticket!=null){

            User user = userService.findUserByName(ticket);

            if (user!=null){
                //让本次请求持有用户
                hostHolder.setUsers(user);
                //构建用户认证结果，存入SecurityContext,以便于授权
                }
            }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",user);
            modelAndView.addObject("type",user.getType());

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        hostHolder.clear();
    }

}
