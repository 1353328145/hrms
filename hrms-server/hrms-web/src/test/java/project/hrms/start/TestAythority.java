package project.hrms.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.hrms.start.entity.Sign;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.mapper.SignMapper;
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

    @Autowired
    SignMapper signMapper;
    @Test
    public void test(){
        Sign sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);
        sign = new Sign();
        sign.setUid(1375013271916019713L);
        signMapper.insert(sign);

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
