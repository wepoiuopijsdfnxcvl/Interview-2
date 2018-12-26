package cn.collabtech.ssm.service;

import cn.collabtech.ssm.bean.Student;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:46
 * @Description:
 */
public interface StudentService {

    /**
     * 查询所有学生
     */
    List<Student> findAllStudent();

    Student findStudentById(int id);

    void addStudent(Student student);

    void delStudentById(int id);

    void updateStudent(Student student);

}
