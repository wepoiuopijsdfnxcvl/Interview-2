package cn.collabtech.ssm.mapper;

import cn.collabtech.ssm.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:43
 * @Description:
 */
@Mapper
@Repository
public interface StudentMapper {
    public List<Student> findAll();

    Student findStudentById(int id);

    void addStudent(Student student);

    void delStudentById(int id);

    void updateStudent(Student student);

}
