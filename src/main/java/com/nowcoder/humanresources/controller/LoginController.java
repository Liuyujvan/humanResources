package com.nowcoder.humanresources.controller;

import com.nowcoder.humanresources.entity.User;
import com.nowcoder.humanresources.service.UserService;
import com.nowcoder.humanresources.util.humanResourcesConstant;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController implements humanResourcesConstant {
    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @GetMapping("/login")
    public String getLoginPage(){
        return "/site/login";
    }


    @PostMapping("/login")
    public String login(String username, String password, HttpServletResponse response, Model model){
        Map<String,Object> map = userService.login(username,password);

        if (map.containsKey("user")){
            User user = (User) map.get("user");
            Cookie cookie =new Cookie("user",user.getUsername());
            cookie.setPath(contextPath);
            cookie.setMaxAge(DEFAULT_EXPIRED_SECONDS);
            response.addCookie(cookie);

            return "redirect:/index";

        }else {
            model.addAttribute("error",map.get("error"));


            return "/site/login";
        }
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "/site/register";
    }
    @PostMapping("/register")
    public String register(String username, String password, int type, HttpServletResponse response,Model model){
        Map<String,Object> map = userService.register(username,password,type);
        if (map.containsKey("user")){
            User user = (User) map.get("user");
            Cookie cookie =new Cookie("user",user.getUsername());
            cookie.setPath(contextPath);
            cookie.setMaxAge(DEFAULT_EXPIRED_SECONDS);
            response.addCookie(cookie);
            return "redirect:/login";

        }else {
            model.addAttribute("error",map.get("error"));


            return "/site/login";
        }
    }

}
