package project.hrms.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Employ;
import project.hrms.start.entity.Notice;
import project.hrms.start.entity.Sign;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.mapper.NoticeMapper;
import project.hrms.start.parameter.TreeNode;
import project.hrms.start.service.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAythority {
    @Autowired
    DepartmentServiceInterface service;

    @Autowired
    EmployMapper employMapper;
    @Autowired
    NoticeServiceInterface noticeService;

    @Autowired
    SignServiceInterface signService;
    @Test
    public void test(){
        Sign sign = new Sign();
        sign.setUid(1374915376185331713L);
        signService.sign(sign,null);
        System.out.println(signService.getSignInfoByUid(1374915376185331713L));
        signService.sign(sign,null);
        System.out.println(signService.getSignInfoByUid(1374915376185331713L));
    }

    @Test
    public void testTree(){
        List<TreeNode> allWithTree = service.getAllWithTree();
        for (TreeNode treeNode : allWithTree) {
            go(treeNode);
        }
    }
    private void go(TreeNode treeNode){
        System.out.println(treeNode);
        List<TreeNode> children = treeNode.getChildren();
        if (children!=null){
            for (TreeNode child : children) {
                go(child);
            }
        }
    }
}
