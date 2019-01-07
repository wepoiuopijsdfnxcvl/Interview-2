package cn.collabtech.ssm.vo;

import cn.collabtech.ssm.bean.Course;

import java.util.List;

/**
 * @Author: Darrick
 * @Date: 2018/10/9 17:26
 * @Description:
 */
public class TeacherVO {

    private Integer teacherId;

    private String name;

    public TeacherVO() {
    }

    public TeacherVO(Integer teacherId, String name) {
        this.teacherId = teacherId;
        this.name = name;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeacherVO{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                '}';
    }
}
