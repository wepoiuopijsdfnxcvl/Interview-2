package cn.collabtech.ssm.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

/**
 * @Auther: Darrick
 * @Date: 2018/10/9 20:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void getAllStudent() throws Exception {
     mvc.perform(MockMvcRequestBuilders.get("/api/student"))
             .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getStudentById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/student/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}