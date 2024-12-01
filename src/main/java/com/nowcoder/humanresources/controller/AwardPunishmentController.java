package com.nowcoder.humanresources.controller;

import com.nowcoder.humanresources.entity.AwardPunishment;
import com.nowcoder.humanresources.entity.Page;
import com.nowcoder.humanresources.service.AwardPunishmentService;
import com.nowcoder.humanresources.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/awardpunishment")
public class AwardPunishmentController {

    @Autowired
    private AwardPunishmentService awardPunishmentService;

    @GetMapping
    public String viewAwardPunishments(Page page,
                                       HttpServletRequest request,
                                       Model model) {
        String username = CookieUtil.getValue(request, "user");
        model.addAttribute("username", username);
        page.setRows(awardPunishmentService.countAllAwardPunishments());
        page.setPath("/awardpunishment");
        List<AwardPunishment> awardPunishments = awardPunishmentService.getAwardPunishments(page.getOffset(), page.getLimit());

        model.addAttribute("awardPunishments", awardPunishments);

        return "site/awardpunishment";
    }

    @GetMapping("/add")
    public String addAwardPunishmentForm(Model model) {
        model.addAttribute("awardPunishment", new AwardPunishment());
        return "site/addAwardPunishment";
    }

    @PostMapping("/add")
    public String addAwardPunishment(@ModelAttribute AwardPunishment awardPunishment, Model model) {
        HashMap<String,Object> map= awardPunishmentService.addAwardPunishment(awardPunishment);
        if (map.containsKey("message")) {
            model.addAttribute("message", map.get("message"));
        } else {
            model.addAttribute("error", map.get("error"));
        }
        return "redirect:/awardpunishment";
    }

    @PostMapping("/delete/{id}")
    public String deleteAwardPunishment(@PathVariable("id") String id, Model model) {
        HashMap<String,Object> map = awardPunishmentService.removeAwardPunishmentById(id);
        if (map.containsKey("message")) {
            model.addAttribute("message", map.get("message"));
        } else {
            model.addAttribute("error", map.get("error"));
        }
        return "redirect:/awardpunishment";
    }

    @GetMapping("/edit/{id}")
    public String editAwardPunishmentForm(@PathVariable("id") String id, Model model) {
        AwardPunishment awardPunishment = awardPunishmentService.getAwardPunishmentById(id);
        if (awardPunishment != null) {
            model.addAttribute("awardPunishment", awardPunishment);
            return "site/editAwardPunishment";
        } else {
            model.addAttribute("error", "未找到该奖惩信息！");
            return "redirect:/awardpunishment";
        }
    }

    @PostMapping("/update")
    public String updateAwardPunishment(@ModelAttribute AwardPunishment awardPunishment, Model model) {
        int result = awardPunishmentService.updateAwardPunishment(awardPunishment);
        if (result > 0) {
            model.addAttribute("message", "奖惩信息更新成功！");
        } else {
            model.addAttribute("error", "奖惩信息更新失败！");
        }
        return "redirect:/awardpunishment";
    }
    @GetMapping("/search")
    public String searchAwardPunishment(@RequestParam("query") String query, Model model) {
        List<AwardPunishment> searchResults;

        if (query.matches("\\d+")) { // Check if query is numeric
            // Search by ID
            AwardPunishment result = awardPunishmentService.getAwardPunishmentById(query);
            searchResults = result != null ? List.of(result) : List.of();
        } else {
            // Search by name
            searchResults = awardPunishmentService.searchByName(query);
        }

        if (searchResults.isEmpty()) {
            model.addAttribute("error", "未找到符合条件的奖惩记录。");
        } else {
            model.addAttribute("awardPunishments", searchResults);
        }
        return "site/awardpunishment";
    }
}
