package com.nowcoder.humanresources.dao;

import com.nowcoder.humanresources.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    Teacher selectTeacherById(String id);
    List<Teacher> selectTeacherByName(String name);
    Teacher getTeacherByName(String name);
    int insertTeacher(Teacher teacher);

    int deleteTeacherById(String id);

    List<Teacher> getAllTeachers(int offset, int limit);

    int updateTeacher(Teacher teacher); // 更新教师信息

    List<Teacher> searchTeachers(@Param("keyword") String keyword,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);
    @Select("SELECT COUNT(*) FROM Teacher")
    int countTeachers();
}
