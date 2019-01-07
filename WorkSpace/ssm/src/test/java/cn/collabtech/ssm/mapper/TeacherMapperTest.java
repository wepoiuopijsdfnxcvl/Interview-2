package cn.collabtech.ssm.mapper;

import cn.collabtech.ssm.bean.Teacher;
import cn.collabtech.ssm.vo.TeacherVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Darrick
 * @Date: 2018/10/29 09:27
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherMapperTest {

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void findAll() {
        List<Teacher> teachers = teacherMapper.findAll();
        System.out.println(teachers.size());
    }

    @Test
    public void insertReturnKey() {
        Teacher teacher = new Teacher();
        teacher.setName("教师测试01");
        teacherMapper.insertReturnKey(teacher);
        System.out.println(teacher.getTeacherId());
    }

    @Test
    public void insertBatchReturnKey() {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Teacher teacher = new Teacher();
            teacher.setName("教师测试 >>> " + i);
            teachers.add(teacher);
        }
        teacherMapper.insertBatchReturnKey(teachers);
        for (Teacher teacher:teachers) {
            System.out.println(teacher.getTeacherId());
        }
    }

    @Test
    public void insertBatchReturnKeyVo() {
        List<TeacherVO> teacherVOS = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TeacherVO teacherVO = new TeacherVO();
            teacherVO.setName("教师测试 >>> " + i);
            teacherVOS.add(teacherVO);
        }
        teacherMapper.insertBatchReturnKeyByVo(teacherVOS);
        for (TeacherVO teacher:teacherVOS) {
            System.out.println(teacher.getTeacherId());
        }
    }
}