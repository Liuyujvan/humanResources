package com.nowcoder.humanresources.dao;

import com.nowcoder.humanresources.entity.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMapper {

    School selectSchoolById(@Param("id") String id);

    int insertSchoolRecord(School school);

    int deleteSchoolRecordById(@Param("id") String id);

    int countSchoolRecords();

    List<School> searchSchoolRecordsByName(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    List<School> getAllSchoolRecords(@Param("offset") int offset, @Param("limit") int limit);

    int updateSchool(School school);

}
