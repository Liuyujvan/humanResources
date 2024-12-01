package com.nowcoder.humanresources.dao;

import com.nowcoder.humanresources.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    // 查询所有部门信息

    // 根据ID查询部门
    Department selectDepartmentById(@Param("id") String id);
    Department selectDepartmentByName(String name);

    // 添加部门信息
    int insertDepartment(Department department);

    // 删除部门信息
    int deleteDepartmentById(@Param("id") String id);

    // 查询部门人数
    int selectNumberByDepartmentId(@Param("id") String id);
//模糊查询
    List<Department> searchDepartmentsByName(@Param("departmentName") String departmentName);
    int updateDepartment(Department department);
    int countDepartments();

    List<Department> getDepartments(@Param("offset") int offset, @Param("limit") int limit);
}
