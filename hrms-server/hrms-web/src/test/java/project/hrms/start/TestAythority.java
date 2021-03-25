package project.hrms.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.hrms.start.entity.Employ;
import project.hrms.start.service.EmployService;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAythority {
    @Autowired
    EmployService service;

    @Test
    public void test(){
        Employ employ = new Employ();
        employ.setPassword("mimashi123");
        employ.setSex(0);
        employ.setName("张三");
        employ.setPhone("1234567891");
        employ.setAddress("安阳市文峰区");
        employ.setNational("汉族");
        employ.setIdNumber("xxxxxx20000101xxxx");
        employ.setCulture("本科");
        employ.setSchool("安阳工学院");
        employ.setEmail("123456@qq.com");
        employ.setContract(new Date());
        employ.setBirthday(new Date());
        employ.setEmployFrom("校招");
        System.out.println(service.save(employ));
    }
}
