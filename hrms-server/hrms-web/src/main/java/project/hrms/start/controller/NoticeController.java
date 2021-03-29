package project.hrms.start.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Notice;
import project.hrms.start.parameter.Msg;
import project.hrms.start.service.NoticeServiceInterface;

import java.util.List;

@RestController
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private NoticeServiceInterface noticeServiceInterface;
    @GetMapping("crud/{pageNum}")
    public Msg getAll(@PathVariable("pageNum") Integer pageNum, Notice notice){
        if (pageNum==null){
            pageNum=1;
        }
        PageHelper.startPage(pageNum,30);
        //查询
        List<Notice> result = noticeServiceInterface.getAllByTitle(notice);
        PageInfo<Notice> pageInfo=new PageInfo<>(result);
        int total = (int) pageInfo.getTotal();
        return Msg.success()
                .add("result",pageInfo.getList())
                .add("total",total);
    }
    @DeleteMapping("crud/{nid}")
    public Msg delete(@PathVariable("nid") Integer nid){
        return noticeServiceInterface.delete(nid)?Msg.success():Msg.fail();
    }
    @PutMapping("crud")
    public Msg update(@RequestBody Notice notice){
        return noticeServiceInterface.update(notice)?Msg.success():Msg.fail();
    }
    @PostMapping("crud")
    public Msg insert(@RequestBody Notice notice){
        return noticeServiceInterface.insert(notice)?Msg.success():Msg.fail();
    }
}
