package cn.collabtech.springcloud.service;

import cn.collabtech.springcloud.entities.Dept;

import java.util.List;

/**
 * @Author: Darrick
 * @Date: 2019/1/24 10:39
 * @Description:
 */
public interface DeptService {

    public boolean add(Dept dept);
    public Dept    get(Long id);
    public List<Dept> list();

}
