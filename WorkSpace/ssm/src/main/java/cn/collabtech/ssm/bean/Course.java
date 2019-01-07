package cn.collabtech.ssm.bean;

import java.util.List;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 17:26
 * @Description:
 */
public class Course {
    private int id;
    private String name;
    private List<Student> students;

    public Course() {
    }

    public Course(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getTeachers() {
        return students;
    }

    public void setTeachers(List<Student> teachers) {
        this.students = teachers;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teachers=" + students +
                '}';
    }
}
