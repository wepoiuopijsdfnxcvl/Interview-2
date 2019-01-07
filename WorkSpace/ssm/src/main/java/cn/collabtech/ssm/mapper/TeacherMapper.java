package cn.collabtech.ssm.mapper;

import cn.collabtech.ssm.bean.Student;
import cn.collabtech.ssm.bean.Teacher;
import cn.collabtech.ssm.vo.TeacherVO;
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
public interface TeacherMapper {
    public List<Teacher> findAll();

    /**
     * 测试插入数据返回主键
     */
    void insertReturnKey(Teacher teacher);

    /**
     * 批量测试插入数据返回主键
     */
    void insertBatchReturnKey(List<Teacher> teachers);

    /**
     * 批量测试插入数据返回主键
     */
    void insertBatchReturnKeyByVo(List<TeacherVO> teacherVOS);

}
