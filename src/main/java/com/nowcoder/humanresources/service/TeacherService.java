package com.nowcoder.humanresources.service;

import com.nowcoder.humanresources.dao.DepartmentMapper;
import com.nowcoder.humanresources.dao.TeacherMapper;
import com.nowcoder.humanresources.entity.Department;
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
public class TeacherService{

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    public Teacher findTeacherById(String id) {
        return teacherMapper.selectTeacherById(id);
    }


    public List<Teacher> findTeacherByName(String name) {
        return teacherMapper.selectTeacherByName(name);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String,Object> insertTeacher(Teacher teacher) {
        HashMap<String, Object> map = new HashMap<>();

        // 检查教师是否已经存在
        if (teacherMapper.selectTeacherById(teacher.getId()) != null) {
            map.put("error", "该工号已存在，请勿重复添加！");
            return map;
        }

        String departmentName = teacher.getDepartment();
        Department department = departmentMapper.selectDepartmentByName(departmentName);

        // 检查部门是否存在
        if (department == null) {
            map.put("error", "该部门不存在，请检查部门名称！");
            return map;
        }

        // 部门存在，增加人数
        department.setNumber(department.getNumber() + 1);
        departmentMapper.updateDepartment(department);

        // 插入教师信息
        int rows = teacherMapper.insertTeacher(teacher);
        if (rows > 0) {
            map.put("message", "教师添加成功！");
        } else {
            map.put("error", "教师添加失败，请重试！");
        }

        return map;
    }



    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String,Object> deleteTeacherById(String id) {
        // 检查工号是否存在
        HashMap<String, Object> response = new HashMap<>();

        // 检查教师是否存在
        Teacher teacher = teacherMapper.selectTeacherById(id);
        if (teacher == null) {
            response.put("error", "教师工号不存在，删除终止！");
            return response;
        }

        // 获取教师所属部门
        String departmentName = teacher.getDepartment();
        Department department = departmentMapper.selectDepartmentByName(departmentName);

        // 如果部门存在且人数大于0，则减一
        if (department != null) {
            if (department.getNumber() > 0) {
                department.setNumber(department.getNumber() - 1);
                departmentMapper.updateDepartment(department);

            } else {
                response.put("error", "所属部门不存在，无法更新人数。");
            }

            // 删除教师信息
            int rows = teacherMapper.deleteTeacherById(id);
            if (rows > 0) {
                response.put("message", "教师删除成功。");
            } else {
                response.put("error", "教师删除失败。");
            }


        }return response;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public HashMap<String,Object> updateTeacher(Teacher teacher) {
        // 检查工号是否存在
        HashMap<String, Object> response = new HashMap<>();

        // 检查教师是否存在
        Teacher existingTeacher = teacherMapper.selectTeacherById(teacher.getId());
        if (existingTeacher == null) {
            response.put("error", "教师工号不存在，修改终止！");
            return response;
        }

        // 获取教师之前的部门
        String oldDepartmentName = existingTeacher.getDepartment();
        Department oldDepartment = departmentMapper.selectDepartmentByName(oldDepartmentName);

        // 如果之前的部门存在且人数大于0，则人数减一
        if (oldDepartment != null && oldDepartment.getNumber() > 0) {
            oldDepartment.setNumber(oldDepartment.getNumber() - 1);
            departmentMapper.updateDepartment(oldDepartment);
        }

        // 获取教师的新部门
        String newDepartmentName = teacher.getDepartment();
        Department newDepartment = departmentMapper.selectDepartmentByName(newDepartmentName);

        // 如果新部门存在，则人数加一
        if (newDepartment != null) {
            newDepartment.setNumber(newDepartment.getNumber() + 1);
            departmentMapper.updateDepartment(newDepartment);
        } else {
            response.put("error", "新部门不存在，无法更新人数！");
            return response;
        }

        // 更新教师信息
        int rows = teacherMapper.updateTeacher(teacher);
        if (rows > 0) {
            response.put("message", "教师信息更新成功，部门人数已更新。");
        } else {
            response.put("error", "教师信息更新失败！");
        }

        return response;
    }




    public List<Teacher> getAllTeachers(int offset, int limit) {
        return teacherMapper.getAllTeachers(offset, limit);
    }


    public List<Teacher> searchTeachers(String keyword, int offset, int limit) {
        return teacherMapper.searchTeachers(keyword, offset, limit);
    }


    public int countTeachers() {
        return teacherMapper.countTeachers();
    }
}
