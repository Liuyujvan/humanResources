package com.nowcoder.humanresources.controller;

import com.nowcoder.humanresources.entity.AwardPunishment;
import com.nowcoder.humanresources.entity.Department;
import com.nowcoder.humanresources.entity.Page;
import com.nowcoder.humanresources.entity.Teacher;
import com.nowcoder.humanresources.service.DepartmentService;
import com.nowcoder.humanresources.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String getDepartments(HttpServletRequest request,
                                 Page page,
                                 Model model) {

        String username = CookieUtil.getValue(request, "user");
        model.addAttribute("username", username);
        page.setRows(departmentService.countAllDepartments());
        page.setPath("/department");
        List<Department> departments = departmentService.getDepartments(page.getOffset(),page.getLimit());
        int totalDepartments = departmentService.countAllDepartments();
        model.addAttribute("departments", departments);

        return "site/department";
    }

    @GetMapping("/search")
    public String searchDepartment(@RequestParam("query") String query, Model model) {
        List<Department> searchResults;

        if (query.matches("\\d+")) { // Check if query is numeric
            // Search by ID
            Department result = departmentService.getDepartmentById(query);
            searchResults = result != null ? List.of(result) : List.of();
        } else {
            // Search by name
            searchResults = departmentService.searchDepartmentsByName(query);
        }

        if (searchResults.isEmpty()) {
            model.addAttribute("error", "未找到符合条件的部门记录。");
        } else {
            model.addAttribute("departments", searchResults);
        }
        return "site/department";
    }

    @PostMapping("/add")
    public String addDepartment(Department department, Model model) {
        boolean success = departmentService.addDepartment(department);
        model.addAttribute("message", success ? "添加成功" : "添加失败");
        return "redirect:/department";
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartmentById(@PathVariable("id")@RequestParam("id") String id, Model model) {
        boolean success = departmentService.deleteDepartmentById(id);
        model.addAttribute("message", success ? "删除成功" : "删除失败");
        return "redirect:/department";
    }

    @GetMapping("/count/{id}")
    @ResponseBody
    public int getDepartmentCount(@PathVariable("id") String id) {
        return departmentService.getDepartmentCount(id);
    }

    @GetMapping("/edit/{id}")
    public String editTeacher(@PathVariable("id") String id, Model model) {
        Department department = departmentService.getDepartmentById(id); // 从数据库获取信息
        model.addAttribute("department", department);
        return "site/editDepartment";  // 确保路径和你的模板目录一致
    }
    @PostMapping("/update")
    public String updateTeacher(@RequestParam("id") String id,
                                @RequestParam("name") String name,
                                @RequestParam("number") int number,
                                Model model) throws ParseException {
      Department department = new Department();
       department.setId(id);
       department.setName(name);
       department.setNumber(number);

       departmentService.updateDepartment(department);


        return "redirect:/department";
    }
}
