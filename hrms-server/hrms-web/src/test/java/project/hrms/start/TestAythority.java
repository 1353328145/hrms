package project.hrms.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Employ;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.service.DepartmentServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAythority {
    @Autowired
    DepartmentServiceInterface service;

    @Autowired
    EmployMapper employMapper;
    @Test
    public void test(){
        employMapper.getCountGroupByDid().forEach(System.out::println);
    }
}
