package com.nowcoder.humanresources.controller;

import com.nowcoder.humanresources.entity.User;
import com.nowcoder.humanresources.service.UserService;
import com.nowcoder.humanresources.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.nowcoder.humanresources.util.humanResourcesConstant.DEFAULT_EXPIRED_SECONDS;


@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private UserService userService;

    // 显示用户信息页面
    @GetMapping("")
    public String showUserProfile(HttpServletRequest request, Model model) {
        // 从session中获取当前用户
        String username = CookieUtil.getValue(request,"user");
        User user = userService.findUserByName(username);
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "/site/user";  // 渲染的页面路径，请确保Thymeleaf模板匹配此路径
    }

    // 更新用户信息（修改密码）
    @PostMapping("/update")
    public String updateUserPassword(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            Model model) {

        // 获取当前登录用户信息
        String username = CookieUtil.getValue(request,"user");
        User user = userService.findUserByName(username);
        if (user == null) {
            return "redirect:/login";
        }

        // 验证旧密码
        if (!userService.checkPassword(user, oldPassword)) {
            model.addAttribute("errorMessage", "旧密码不正确，请重试。");
            model.addAttribute("user", user);
            return "/site/user";
        }

        // 更新密码
        user.setPassword(newPassword);
        userService.updateUser(user);

        // 更新session中的用户信息
        Cookie cookie =new Cookie("user",user.getUsername());
        cookie.setPath(contextPath);
        cookie.setMaxAge(DEFAULT_EXPIRED_SECONDS);
        response.addCookie(cookie);

        model.addAttribute("successMessage", "密码更新成功！");
        return "/site/user";
    }
}
