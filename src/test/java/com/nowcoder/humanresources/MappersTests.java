package com.nowcoder.humanresources;

import com.nowcoder.humanresources.dao.*;
import com.nowcoder.humanresources.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = HumanResourcesApplication.class)
public class MappersTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    private Department testDepartment;
    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setType(1);

        userMapper.insertUser(user);
    }
    @Test
    public void testUpdatePassword(){
        userMapper.updatePassword("testUser","test02");
    }

   @Test
    public void testSelectUser(){
        userMapper.selectByName("testUser");
   }
    @Test
    public void testSelectTeacher(){
        teacherMapper.selectTeacherById("4");
    }

    @Test
    public void testSelectTeacherById() {
        String teacherId = "1"; // 假设数据库中存在 id 为 1 的教师
        Teacher teacher = teacherMapper.selectTeacherById(teacherId);
        System.out.println(teacher);
    }

    @Test
    public void testInsertTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId("test");
        teacher.setName("testName");
        teacher.setEmail("1234@qq.com");
        teacher.setSex("M");
        teacher.setDepartment("计算机系");
        teacher.setTitle("教授");
        teacher.setBirthday(new Date());
        teacher.setStudentCode("11111");
        teacher.setAwardPunishmentId("123");

        int result = teacherMapper.insertTeacher(teacher);
        System.out.println("Insert result: " + result);
    }

    @Test
    public void testDeleteTeacherById() {
        String teacherId = "test"; // 假设数据库中存在 id 为 2 的教师
        int result = teacherMapper.deleteTeacherById(teacherId);
        System.out.println("Delete result: " + result);
    }

    @Test
    public void testGetAllTeachers() {
        int offset = 0;
        int limit = 10; // 每页显示 10 条记录
        List<Teacher> teachers = teacherMapper.getAllTeachers(offset, limit);
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    @Test
    public void testUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId("1"); // 假设数据库中存在 id 为 1 的教师
        teacher.setName("Update"); // 使用符合长度限制的名称
        teacher.setEmail("short@ex.com"); // 使用符合长度限制的电子邮件
        teacher.setSex("F");
        teacher.setDepartment("物理系");
        teacher.setTitle("副教授");
        teacher.setBirthday(new Date());
        teacher.setStudentCode("22222");
        teacher.setAwardPunishmentId("321");

        int result = teacherMapper.updateTeacher(teacher);
        System.out.println("Update result: " + result);
    }

    @Test
    public void testSearchTeachers() {
        String keyword = "计算机"; // 搜索关键词
        int offset = 0;
        int limit = 10; // 每页显示 10 条记录
        List<Teacher> teachers = teacherMapper.searchTeachers(keyword, offset, limit);
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
}


    @Test
    public void testInsertDepartment() {
        Department department = new Department();
        department.setId("1234");
        department.setName("研发部");
        department.setNumber(15);

        int result = departmentMapper.insertDepartment(department);
        Assertions.assertEquals(1, result);
        System.out.println("插入部门成功，部门ID：" + department.getId());
    }
    @Test
    public void testSelectDepartmentById() {
        Department department = departmentMapper.selectDepartmentById("1234");
        Assertions.assertNotNull(department, "部门未找到");
        System.out.println("查询部门成功，部门名称：" + department.getName());
    }

    @Test
    public void testDeleteDepartmentById() {
        int result = departmentMapper.deleteDepartmentById("1234");
        Assertions.assertEquals(1, result, "删除部门失败");
        System.out.println("删除部门成功");
    }
    @Test
    public void testSelectNumberByDepartmentId() {
        int number = departmentMapper.selectNumberByDepartmentId("2");
        Assertions.assertTrue(number >= 0, "查询人数失败");
        System.out.println("查询部门人数成功，人数：" + number);
    }

    @Test
    public void testSearchDepartmentsByName() {
        String departmentName = "研";
        List<Department> departments = departmentMapper.searchDepartmentsByName(departmentName);
        Assertions.assertNotNull(departments, "查询失败");
        Assertions.assertTrue(departments.size() > 0, "未找到匹配的部门");
        System.out.println("模糊查询成功，匹配部门数量：" + departments.size());
    }
    @Test
    public void testCountDepartments() {
        int count = departmentMapper.countDepartments();
        Assertions.assertTrue(count >= 0, "部门总数查询失败");
        System.out.println("查询部门总数成功，总数：" + count);
    }
    @Test
    public void testGetDepartments() {
        int offset = 0;
        int limit = 5;
        List<Department> departments = departmentMapper.getDepartments(offset, limit);
        Assertions.assertNotNull(departments, "查询失败");
        Assertions.assertTrue(departments.size() > 0, "未找到任何部门");
        System.out.println("分页查询部门成功，查询到部门数量：" + departments.size());
    }


    @Autowired
    private SchoolMapper schoolMapper;

    @Test
    public void testSelectSchoolById() {
        School school = schoolMapper.selectSchoolById("S654321");
        Assertions.assertNotNull(school, "未找到学籍记录");
        Assertions.assertEquals("John Doe", school.getName(), "名字不匹配");
    }

    @Test
    public void testInsertSchoolRecord() {
        School newSchool = new School();
        newSchool.setId("S654321");
        newSchool.setStudentCode("STU2024");
        newSchool.setName("Jane Smith");
        newSchool.setPrimarySchool("Primary School B");
        newSchool.setJuniorHigh("Junior High C");
        newSchool.setSeniorHigh("Senior High D");
        newSchool.setUndergraduate("University Y");
        newSchool.setGraduate("Graduate School Z");
        newSchool.setDoctorate("Doctorate X");

        int result = schoolMapper.insertSchoolRecord(newSchool);
        Assertions.assertEquals(1, result, "插入学籍记录失败");

        School insertedSchool = schoolMapper.selectSchoolById("S654321");
        Assertions.assertNotNull(insertedSchool, "未找到插入的学籍记录");
        Assertions.assertEquals("Jane Smith", insertedSchool.getName(), "插入记录的名字不匹配");
    }

    @Test
    public void testDeleteSchoolRecordById() {
        int result = schoolMapper.deleteSchoolRecordById("S654321");
        Assertions.assertEquals(1, result, "删除学籍记录失败");

        School deletedSchool = schoolMapper.selectSchoolById("S123456");
        Assertions.assertNull(deletedSchool, "学籍记录未成功删除");
    }

    @Test
    public void testCountSchoolRecords() {
        int count = schoolMapper.countSchoolRecords();
        Assertions.assertTrue(count > 0, "学籍记录总数应大于 0");
        System.out.println("当前学籍记录总数: " + count);
    }

    @Test
    public void testSearchSchoolRecordsByName() {
        List<School> schools = schoolMapper.searchSchoolRecordsByName("李四", 0, 10);
        Assertions.assertNotNull(schools, "未找到匹配的学籍记录");
        Assertions.assertFalse(schools.isEmpty(), "应找到至少一个匹配的学籍记录");
        System.out.println("匹配的学籍记录数量: " + schools.size());
    }

    @Test
    public void testGetAllSchoolRecords() {
        List<School> schools = schoolMapper.getAllSchoolRecords(0, 10);
        Assertions.assertNotNull(schools, "未找到任何学籍记录");
        Assertions.assertFalse(schools.isEmpty(), "学籍记录列表不应为空");
        System.out.println("学籍记录列表数量: " + schools.size());
    }

    @Autowired
    private AwardPunishmentMapper awardPunishmentMapper;

    @Test
    public void testInsertAwardPunishment() {
        AwardPunishment awardPunishment = new AwardPunishment("AP001", "John Doe", "Outstanding Employee", "None", "2023-01-01", "N/A");
        int result = awardPunishmentMapper.insertAwardPunishment(awardPunishment);
        Assertions.assertEquals(1, result, "插入奖惩记录失败");
    }

    @Test
    public void testSelectAwardPunishmentById() {
        String id = "AP001";
        AwardPunishment awardPunishment = awardPunishmentMapper.selectAwardPunishmentById(id);
        Assertions.assertNotNull(awardPunishment, "未找到对应的奖惩记录");
        Assertions.assertEquals("John Doe", awardPunishment.getName(), "奖惩记录姓名不匹配");
    }

    @Test
    public void testUpdateAwardPunishment() {
        AwardPunishment awardPunishment = new AwardPunishment("AP001", "John Doe", "Employee of the Year", "None", "2023-01-01", "N/A");
        int result = awardPunishmentMapper.updateAwardPunishment(awardPunishment);
        Assertions.assertEquals(1, result, "更新奖惩记录失败");

        AwardPunishment updatedRecord = awardPunishmentMapper.selectAwardPunishmentById("AP001");
        Assertions.assertEquals("Employee of the Year", updatedRecord.getAward(), "奖惩信息更新不正确");
    }

    @Test
    public void testDeleteAwardPunishmentById() {
        String id = "AP001";
        int result = awardPunishmentMapper.deleteAwardPunishmentById(id);
        Assertions.assertEquals(1, result, "删除奖惩记录失败");

        AwardPunishment deletedRecord = awardPunishmentMapper.selectAwardPunishmentById(id);
        Assertions.assertNull(deletedRecord, "奖惩记录未被正确删除");
    }

    @Test
    public void testGetAllAwardPunishments() {
        List<AwardPunishment> awardPunishments = awardPunishmentMapper.getAllAwardPunishments(0, 10);
        Assertions.assertNotNull(awardPunishments, "奖惩记录列表为空");
        Assertions.assertTrue(awardPunishments.size() > 0, "奖惩记录列表为空");
    }

    @Test
    public void testCountAwardPunishments() {
        int count = awardPunishmentMapper.countAwardPunishments();
        Assertions.assertTrue(count >= 0, "奖惩记录数量应为非负值");
    }

}
