package com.nowcoder.humanresources.controller;

import com.nowcoder.humanresources.entity.Page;
import com.nowcoder.humanresources.entity.Teacher;
import com.nowcoder.humanresources.service.TeacherService;
import com.nowcoder.humanresources.service.UserService;
import com.nowcoder.humanresources.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.MidiFileFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/index")
    public String getIndexPage(HttpServletRequest request, Model model,
                               Page page) {
        String username = CookieUtil.getValue(request, "user");
        model.addAttribute("username", username);
        page.setRows(teacherService.countTeachers());
        page.setPath("/index");
        List<Teacher> teachers = teacherService.getAllTeachers(page.getOffset(), page.getLimit());
        model.addAttribute("teachers", teachers);


        return "/index";
    }

    @PostMapping("/teacher/add")
    public String addTeacher(@RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("sex") String sex,
                             @RequestParam("email") String email,
                             @RequestParam("birthday") String birthday,
                             @RequestParam("title") String title,
                             @RequestParam("department") String department,
                             Model model) throws ParseException {


            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setName(name);
            teacher.setSex(sex);
            teacher.setEmail(email);

            // 将字符串转换为 Date 对象
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            teacher.setBirthday(dateFormat.parse(birthday));

            teacher.setTitle(title);
            teacher.setDepartment(department);
            HashMap<String,Object> map = teacherService.insertTeacher(teacher);
            if (map.containsKey("message")) {
                model.addAttribute("message", map.get("message"));
            }else {
                model.addAttribute("error",map.get("error"));
            }
            return "redirect:/index";
        }

    @GetMapping("/teacher/edit/{id}")
    public String editTeacher(@PathVariable("id") String id, Model model) {
        Teacher teacher = teacherService.findTeacherById(id);  // 从数据库获取教师信息
        model.addAttribute("teacher", teacher);
        return "site/editTeacher";  // 确保路径和你的模板目录一致
    }


    @GetMapping("/teacher/search")
    public String searchTeacher(@RequestParam("query") String query, Model model) {
        List<Teacher> searchResults;

        if (query.matches("\\d+")) { // Check if query is numeric
            // Search by ID
            Teacher result = teacherService.findTeacherById(query);
            searchResults = result != null ? List.of(result) : List.of();
        } else {
            // Search by name
            searchResults = teacherService.findTeacherByName(query);
        }

        if (searchResults.isEmpty()) {
            model.addAttribute("error", "未找到符合条件的教师记录。");
        } else {
            model.addAttribute("teachers", searchResults);
        }
        return "/index";
    }


    @PostMapping("/teacher/update")
    public String updateTeacher(@RequestParam("id") String id,
                                @RequestParam("name") String name,
                                @RequestParam("sex") String sex,
                                @RequestParam("email") String email,
                                @RequestParam("birthday") String birthday,
                                @RequestParam("title") String title,
                                @RequestParam("department") String department,
                                Model model) throws ParseException {

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setEmail(email);

        // 将字符串转换为 Date 对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        teacher.setBirthday(dateFormat.parse(birthday));

        teacher.setTitle(title);
        teacher.setDepartment(department);
        HashMap<String,Object> map = teacherService.updateTeacher(teacher);

        if (map.containsKey("message")) {
            model.addAttribute("message", map.get("message"));
        }else {
            model.addAttribute("error",map.get("error"));
        }

        return "redirect:/index";
    }

    @PostMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable("id") String id,Model model) {
        HashMap<String,Object> map = teacherService.deleteTeacherById(id);
        if (map.containsKey("message")){
            model.addAttribute("message",map.get("message"));
        }else {
            model.addAttribute("error",map.get("error"));
        }
        return "redirect:/index";
    }
}
