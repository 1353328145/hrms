package project.hrms.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Employ;
import project.hrms.start.entity.Notice;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.mapper.NoticeMapper;
import project.hrms.start.parameter.TreeNode;
import project.hrms.start.service.DepartmentServiceInterface;
import project.hrms.start.service.NoticeService;
import project.hrms.start.service.NoticeServiceInterface;

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
    @Test
    public void test(){
//        Notice notice =new Notice();
//        notice.setUid(1374915376185331713L);
//        notice.setTitle("另一个通知");
//        notice.setContent("以后实行996工作制,这是你们的福报!");
////
//        if(noticeService.insert(notice)){
//            System.out.println("插入成功");
//            noticeService.getAllByTitle(new Notice()).forEach(System.out::println);
//        }
//        noticeService.getAllByTitle(new Notice()).forEach(System.out::println);
//
//        System.out.println("-------------------------------------------");
//        notice.setContent("以后实行996工作制,这是你们的福报!");
//        if(noticeService.update(notice)){
//            System.out.println("修改成功");
//            noticeService.getAllByTitle(new Notice()).forEach(System.out::println);
//        }
//        System.out.println("-------------------------------------------");

//        if(noticeService.delete(4)){
//            System.out.println("删除成功");
//            noticeService.getAllByTitle(new Notice()).forEach(System.out::println);
//        }
//        System.out.println("-------------------------------------------");
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
