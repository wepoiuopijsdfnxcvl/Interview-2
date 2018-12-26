package cn.collabtech.ssm.controller;

import cn.collabtech.ssm.bean.Student;
import cn.collabtech.ssm.bean.Teacher;
import cn.collabtech.ssm.service.StudentService;
import cn.collabtech.ssm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:45
 * @Description:
 */
@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacher")
    public List<Teacher> getAllStudent(){
        List<Teacher> teachers = teacherService.findAllTeacher();
        return teachers;
    }

}
