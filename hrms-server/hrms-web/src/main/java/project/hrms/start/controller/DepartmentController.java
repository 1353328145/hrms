package project.hrms.start.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Role;
import project.hrms.start.parameter.Msg;
import project.hrms.start.service.DepartmentServiceInterface;
import project.hrms.start.service.EmployServiceInterface;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private DepartmentServiceInterface departmentService;

    @Autowired
    private EmployServiceInterface employServiceInterface;
    @GetMapping("dp/{pageNum}")
    public Msg getDp(@PathVariable("pageNum") Integer pageNum, Department department){
        if (pageNum==null){
            pageNum=1;
        }
        PageHelper.startPage(pageNum,30);
        //查询
        List<Department> result = departmentService.getDepartmentByInfo(department);
        PageInfo<Department> pageInfo=new PageInfo<>(result);
        int total = (int) pageInfo.getTotal();
        return Msg.success()
                .add("result",pageInfo.getList())
                .add("total",total);
    }

    @GetMapping("all")
    public Msg getAllDp(){
        return Msg.success().add("dp",departmentService.getAll());
    }
    @PostMapping("dp")
    public Msg insertDp(@RequestBody Department department){
        return departmentService.insert(department)?Msg.success():Msg.fail();
    }

    @DeleteMapping("dp/{did}")
    public Msg deleteDp(@PathVariable("did") Integer did){
        return departmentService.deleteByDid(did)?Msg.success():Msg.fail();
    }

    @PutMapping("dp")
    public Msg updateDp(@RequestBody Department department){
        return departmentService.updateDepartmentByDid(department)?Msg.success():Msg.fail();
    }

    @GetMapping("getCountByDp")
    public Msg employInfo(){
        return Msg.success().add("info",departmentService.getEmployInfoByDp());
    }

    @GetMapping("getDpCount")
    public Msg getDpCount(){
        return Msg.success().add("dpCount",departmentService.getCount()).add("emCount",employServiceInterface.getCount());
    }

    @GetMapping("getTree")
    public Msg getTree(){
        return Msg.success().add("tree",departmentService.getAllWithTree());
    }
}
