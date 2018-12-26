package cn.collabtech.ssm.service;

import cn.collabtech.ssm.bean.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 20:30
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void findStudentById(){
        Student student = studentService.findStudentById(2);
        //断言
        Assert.assertEquals(11,student.getAge());
    }


}