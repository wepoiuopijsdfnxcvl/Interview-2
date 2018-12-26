package cn.collabtech.ssm.VODTO;

import cn.collabtech.ssm.bean.Teacher;
import cn.collabtech.ssm.vo.TeacherVO;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @Author: Darrick
 * @Date: 2018/10/29 09:54
 * @Description:
 */
public class TestVODTO {

    @Test
    public void TestPOToVO(){
        Teacher teacher = new Teacher(1,"张老师",null);
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(teacher,teacherVO);
        System.out.println(teacherVO);
    }

}
