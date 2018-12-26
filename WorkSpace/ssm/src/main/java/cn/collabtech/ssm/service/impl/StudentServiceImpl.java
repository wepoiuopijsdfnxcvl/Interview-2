package cn.collabtech.ssm.service.impl;

import cn.collabtech.ssm.bean.Student;
import cn.collabtech.ssm.mapper.StudentMapper;
import cn.collabtech.ssm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:47
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> findAllStudent() {
        List<Student> studentList = studentMapper.findAll();
        return studentList;
    }

    @Override
    public Student findStudentById(int id) {
        Student student = studentMapper.findStudentById(id);
        return student;
    }

    @Override
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
    }

    @Override
    public void delStudentById(int id) {
        studentMapper.delStudentById(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }
}
