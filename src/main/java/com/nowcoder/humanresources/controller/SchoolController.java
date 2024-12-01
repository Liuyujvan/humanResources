package com.nowcoder.humanresources.controller;

import com.nowcoder.humanresources.entity.Department;
import com.nowcoder.humanresources.entity.Page;
import com.nowcoder.humanresources.entity.School;
import com.nowcoder.humanresources.service.SchoolService;
import com.nowcoder.humanresources.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/school")
@Controller
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public String getSchoolRecords(Page page,
                                   HttpServletRequest request,
                                   Model model) {
        String username = CookieUtil.getValue(request, "user");
        model.addAttribute("username", username);
        page.setRows(schoolService.getSchoolRecordCount());
        page.setPath("/school");
        List<School> schoolRecords = schoolService.getAllSchoolRecords(page.getOffset(),page.getLimit());


        model.addAttribute("schoolRecords", schoolRecords);


        return "site/school";
    }



    // 添加新的学籍记录
    @GetMapping("/add")
    public String addSchoolRecordForm(Model model) {
        model.addAttribute("school", new School());
        return "redirect:/school";
    }

    @PostMapping("/add")
    public String addSchoolRecord(@ModelAttribute School school, Model model) {
       HashMap<String,Object> map = schoolService.addSchoolRecord(school);
       if (map.containsKey("error")){
           model.addAttribute("error",map.get("error"));
       }else {
           model.addAttribute("message",map.get("message"));
       }
        return "redirect:/school";
    }

    // 删除学籍记录
    @PostMapping("/delete/{id}")
    public String deleteSchoolRecord(@PathVariable("id") String id, Model model) {
        HashMap<String,Object> map = schoolService.removeSchoolRecordById(id);
        if (map.containsKey("error")){
            model.addAttribute("error",map.get("error"));
        }else {
            model.addAttribute("message",map.get("message"));
        }
        return "redirect:/school";
    }

    @GetMapping("/search")
    public String searchSchool(@RequestParam("query") String query, Model model, Page page) {
        List<School> searchResults;

        if (query.matches("\\d+")) { // Check if query is numeric
            // Search by ID
            School result = schoolService.getSchoolRecordById(query);
            searchResults = result != null ? List.of(result) : List.of();
        } else {
            page.setRows(schoolService.getSchoolRecordCount());
            page.setPath("/school");
            // Search by name
            searchResults = schoolService.searchSchoolRecordsByName(query,page.getOffset(),page.getLimit());
        }

        if (searchResults.isEmpty()) {
            model.addAttribute("error", "未找到符合条件的学籍记录。");
        } else {
            model.addAttribute("schoolRecords", searchResults);
        }
        return "site/school";
    }

    @GetMapping("/edit/{id}")
    public String editSchool(@PathVariable("id") String id, Model model) {
       School school = schoolService.getSchoolRecordById(id); // 从数据库获取信息
        model.addAttribute("school", school);
        return "site/editSchool";  // 确保路径和你的模板目录一致
    }
    @PostMapping("/update")
    public String updateSchool(@RequestParam("id") String id,
                               @RequestParam("name") String name,
                               @RequestParam("studentCode") String studentCode,
                               @RequestParam("primarySchool") String primarySchool,
                               @RequestParam("juniorHigh") String juniorHigh,
                               @RequestParam("seniorHigh") String seniorHigh,
                               @RequestParam("undergraduate") String undergraduate,
                               @RequestParam("graduate") String graduate,
                               @RequestParam("doctorate") String doctorate,
                               Model model) {
        School school = new School();
        school.setId(id);
        school.setName(name);
        school.setStudentCode(studentCode);
        school.setPrimarySchool(primarySchool);
        school.setJuniorHigh(juniorHigh);
        school.setSeniorHigh(seniorHigh);
        school.setUndergraduate(undergraduate);
        school.setGraduate(graduate);
        school.setDoctorate(doctorate);

        int result = schoolService.updateSchool(school);
        if (result > 0) {
            model.addAttribute("message", "学籍记录更新成功！");
        } else {
            model.addAttribute("error", "学籍记录更新失败，请检查输入数据。");
        }

        return "redirect:/school";
    }
}
