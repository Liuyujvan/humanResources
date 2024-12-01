package com.nowcoder.humanresources.service;

import com.nowcoder.humanresources.dao.AwardPunishmentMapper;
import com.nowcoder.humanresources.dao.TeacherMapper;
import com.nowcoder.humanresources.entity.AwardPunishment;
import com.nowcoder.humanresources.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AwardPunishmentService {

    @Autowired
    private AwardPunishmentMapper awardPunishmentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    public AwardPunishment getAwardPunishmentById(String id) {
        return awardPunishmentMapper.selectAwardPunishmentById(id);
    }

    public List<AwardPunishment> searchByName(String name) {
        return awardPunishmentMapper.selectByName(name);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String,Object> addAwardPunishment(AwardPunishment awardPunishment) {
        Teacher teacher = teacherMapper.getTeacherByName(awardPunishment.getName());
        HashMap<String,Object> map = new HashMap<>();
        if (teacher==null){
            map.put("error","被添加奖惩教师不存在！请重新输入信息！");
        }else if(!teacher.getName().equals(awardPunishment.getName())){
            map.put("error","被添加奖惩教师名输入错误！请重新输入信息！");
        }
        int result = awardPunishmentMapper.insertAwardPunishment(awardPunishment);
        if (result > 0) {
            // 将生成的 awardPunishmentId 设置给指定的教师
            String awardPunishmentId = awardPunishment.getId();

            teacher.setAwardPunishmentId(awardPunishment.getName());
            teacherMapper.updateTeacher(teacher);
            map.put("message","成功添加教师奖惩信息！");
        }
        return map;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String,Object> removeAwardPunishmentById(String id) {
        HashMap<String,Object> map = new HashMap<>();
        AwardPunishment awardPunishment = awardPunishmentMapper.selectAwardPunishmentById(id);
        if(awardPunishment==null){
            map.put("error","该教师的奖励惩罚信息不存在！");
        }
        List<AwardPunishment> awardPunishments = awardPunishmentMapper.selectByName(awardPunishment.getName());
        if (awardPunishments.size()==1){
            Teacher teacher = teacherMapper.getTeacherByName(awardPunishment.getName());
            teacher.setAwardPunishmentId("");
            teacherMapper.updateTeacher(teacher);
            awardPunishmentMapper.deleteAwardPunishmentById(id);
            map.put("message","删除奖惩信息成功！");

        }else {
            awardPunishmentMapper.deleteAwardPunishmentById(id);
            map.put("message","删除奖惩信息成功！");
        }
        return map;
    }

    public int updateAwardPunishment(AwardPunishment awardPunishment) {
        return awardPunishmentMapper.updateAwardPunishment(awardPunishment);
    }

    public List<AwardPunishment> getAwardPunishments(int offset, int limit) {
        return awardPunishmentMapper.getAllAwardPunishments(offset, limit);
    }

    public int countAllAwardPunishments() {
        return awardPunishmentMapper.countAwardPunishments();
    }
}
