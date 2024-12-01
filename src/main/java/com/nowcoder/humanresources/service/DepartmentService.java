package com.nowcoder.humanresources.service;

import com.nowcoder.humanresources.dao.DepartmentMapper;
import com.nowcoder.humanresources.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public Department getDepartmentById(String id) {
        return departmentMapper.selectDepartmentById(id);
    }


    public boolean addDepartment(Department department) {
        return departmentMapper.insertDepartment(department) > 0;
    }

    public boolean deleteDepartmentById(String id) {
        return departmentMapper.deleteDepartmentById(id) > 0;
    }

    public int getDepartmentCount(String id) {
        return departmentMapper.selectNumberByDepartmentId(id);
    }

    public List<Department> searchDepartmentsByName(String departmentName) {
        return departmentMapper.searchDepartmentsByName(departmentName);
    }

    public int countAllDepartments() {
        return departmentMapper.countDepartments();
    }

    public List<Department> getDepartments(int offset, int limit) {
        return departmentMapper.getDepartments(offset,limit);
    }
    public int updateDepartment(Department department){
        return departmentMapper.updateDepartment(department);
    }
}
