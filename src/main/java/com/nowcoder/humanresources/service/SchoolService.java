package com.nowcoder.humanresources.service;

import com.nowcoder.humanresources.dao.SchoolMapper;
import com.nowcoder.humanresources.dao.TeacherMapper;
import com.nowcoder.humanresources.entity.School;
import com.nowcoder.humanresources.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private TeacherMapper teacherMapper;


    // 根据ID获取学籍记录
    public School getSchoolRecordById(String id) {
        return schoolMapper.selectSchoolById(id);
    }

    // 添加学籍记录
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String, Object> addSchoolRecord(School school) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Teacher teacher = teacherMapper.getTeacherByName(school.getName());
        if (teacher==null){
            resultMap.put("error","教师不存在，请重新输入学籍信息！");
            return resultMap;
        }
        if (teacher.getStudentCode()!=null){
            resultMap.put("error","该教师学籍信息已存在，请勿重复添加！");
            return resultMap;
        }


        // 插入学籍记录
        int insertResult = schoolMapper.insertSchoolRecord(school);
        if (insertResult > 0) {
            // 获取 studentCode
            String studentCode = school.getStudentCode();

            // 获取与 studentCode 关联的教师
            teacher.setStudentCode(studentCode);
            teacherMapper.updateTeacher(teacher);

            // 如果找到该教师，更新其 studentCode
            resultMap.put("message","成功添加该教师学籍信息！");


            }
        return resultMap;
    }

    // 根据ID删除学籍记录
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String, Object> removeSchoolRecordById(String id) {
        HashMap<String,Object> map = new HashMap<>();
        Teacher teacher = teacherMapper.selectTeacherById(id);
        if (teacher==null){
            map.put("error","该教师不存在！");

        }
        int result = schoolMapper.deleteSchoolRecordById(id);
        if (result>0){
            teacher.setStudentCode("");
            teacherMapper.updateTeacher(teacher);
        }else {
            map.put("error","删除学籍失败，请稍后重试！");
        }
        return map;
    }

    // 获取学籍记录总数
    public int getSchoolRecordCount() {
        return schoolMapper.countSchoolRecords();
    }

    // 根据姓名进行模糊查询
    public List<School> searchSchoolRecordsByName(String name, int offset, int limit) {
        return schoolMapper.searchSchoolRecordsByName(name, offset, limit);
    }

    // 分页获取所有学籍记录
    public List<School> getAllSchoolRecords(int offset, int limit) {
        return schoolMapper.getAllSchoolRecords(offset, limit);

    }
    public int updateSchool(School school) {
        return schoolMapper.updateSchool(school);
    }

}
