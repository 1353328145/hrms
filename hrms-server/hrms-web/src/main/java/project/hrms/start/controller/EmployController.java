package project.hrms.start.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.hrms.start.parameter.Msg;
import project.hrms.start.entity.Employ;
import project.hrms.start.service.EmployService;
import project.hrms.start.service.EmployServiceInterface;

import java.util.List;

@RestController
@RequestMapping("employ")
public class EmployController {
    @Autowired
    private EmployServiceInterface employService;

    @GetMapping("loadEmployForAuth/{pageNum}")
    public Msg loadEmployForAuth(Employ employ, @PathVariable("pageNum") Integer pageNum){
        if (pageNum==null){
            pageNum=1;
        }
        PageHelper.startPage(pageNum,30);
        //查询
        List<Employ> result = employService.getEmployForAuth(employ);
        PageInfo<Employ> pageInfo=new PageInfo<>(result);
        int total = (int) pageInfo.getTotal();
        return Msg.success()
                .add("result",pageInfo.getList())
                .add("total",total);
    }
}
