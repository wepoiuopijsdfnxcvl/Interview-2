package cn.collabtech.ssm.controller;

import cn.collabtech.ssm.bean.Student;
import cn.collabtech.ssm.service.StudentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:45
 * @Description:
 */

@Api("StudentController相关API")
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "查询所有学生信息",notes = "查询数据库中所有学生信息")
    @GetMapping("/student")
    public List<Student> getAllStudent() {
        List<Student> students = studentService.findAllStudent();
        return students;
    }

    @ApiOperation(value = "根据id查询学生的信息",notes = "查询数据库中某个学生的信息")
    @ApiImplicitParam(name = "id", value = "学生ID", paramType = "path", required = true, dataType = "Integer")
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        Student student = studentService.findStudentById(id);
        return student;
    }

    @ApiOperation(value = "根据id删除学生的信息",notes = "删除数据库中某个学生的信息")
    @ApiImplicitParam(name = "id", value = "学生ID", paramType = "path", required = true, dataType = "Integer")
    @DeleteMapping("/student/{id}")
    public void delStudent(@PathVariable Integer id) {
        studentService.delStudentById(id);
    }

    @ApiOperation(value = "新增学生信息",notes = "向数据库新增学生信息")
    @PostMapping("/student")
    public String addStudent(Student student) {
        try {
            studentService.addStudent(student);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
    @ApiOperation(value = "更新学生信息",notes = "向数据库更新学生信息")
    @PutMapping("/student")
    public String updateStudent(Student student) {
        try {
            studentService.updateStudent(student);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

}
