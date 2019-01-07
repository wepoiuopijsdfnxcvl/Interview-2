package cn.collabtech.ssm.service.impl;

import cn.collabtech.ssm.bean.Student;
import cn.collabtech.ssm.bean.Teacher;
import cn.collabtech.ssm.mapper.StudentMapper;
import cn.collabtech.ssm.mapper.TeacherMapper;
import cn.collabtech.ssm.service.StudentService;
import cn.collabtech.ssm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:47
 * @Description:
 */
@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findAllTeacher() {
        List<Teacher> teacherList = teacherMapper.findAll();
        return teacherList;
    }
}
