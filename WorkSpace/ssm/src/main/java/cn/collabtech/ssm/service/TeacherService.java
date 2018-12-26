package cn.collabtech.ssm.service;

import cn.collabtech.ssm.bean.Teacher;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 16:46
 * @Description:
 */
public interface TeacherService {

    /**
     * 查询所有老师及其课堂
     */
    public List<Teacher> findAllTeacher();

}
