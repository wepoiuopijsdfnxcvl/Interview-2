package cn.collabtech.ssm.bean;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 17:26
 * @Description:
 */
public class Teacher {

    private Integer teacherId;

    private String name;


    private Date createTime;

    private List<Course> courses;

    public Teacher() {
    }

    public Teacher(Integer teacherId, String name, List<Course> courses) {
        this.teacherId = teacherId;
        this.name = name;
        this.courses = courses;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
