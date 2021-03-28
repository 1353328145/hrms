package project.hrms.start.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.hrms.start.parameter.MenuUpdate;
import project.hrms.start.parameter.Msg;
import project.hrms.start.entity.Employ;
import project.hrms.start.entity.Menu;
import project.hrms.start.entity.Role;
import project.hrms.start.service.AuthorityService;
import project.hrms.start.service.AuthorityServiceInterface;
import project.hrms.start.service.EmployService;
import project.hrms.start.service.EmployServiceInterface;
import project.hrms.start.util.TokenUtil;

import java.util.List;

@RestController
@RequestMapping("authority")
public class AuthorityController {
    @Autowired
    private EmployServiceInterface employService;

    @Autowired
    private AuthorityServiceInterface authorityService;
    /**
     * 登录
      */
    @PostMapping("login")
    public Msg Login(@RequestBody Employ employ){
        Long uid = employ.getUid();
        String password = employ.getPassword();
        if (uid==null||password==null){
            return Msg.fail("参数为空!");
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(uid.toString(), password));
        }catch (UnknownAccountException e){
            return Msg.fail("用户名不存在!");
        }catch (IncorrectCredentialsException e){
            return Msg.fail("密码错误!");
        }
        Employ currentEmploy = employService.getEmployByUid(uid);
        return Msg.success().add("uid",uid).add("token", TokenUtil.sign(currentEmploy));
    }

    @PostMapping("register")
    public Msg register(@RequestBody Employ employ){
        Long uid;
        try {
            uid = employService.save(employ);
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail();
        }
        return uid != null?Msg.success().add("uid",uid):Msg.fail();
    }

    @GetMapping("loadAllMenu/{uid}")
    public Msg loadAllMenu(@PathVariable("uid") Long uid){
        List<Menu> res = authorityService.getMenuByUid(uid);
        return  res == null || res.size() == 0 ? Msg.fail():Msg.success().add("menus",res);
    }

    @GetMapping("loadAllRole")
    public Msg loadAllRole(){
        return Msg.success().add("role",authorityService.getAllRole());
    }
    @GetMapping("loadAllRoleByQueryInfo/{pageNum}")
    public Msg loadAllRoleByQueryInfo(@PathVariable("pageNum") Integer pageNum, Role role){
        if (pageNum==null){
            pageNum=1;
        }
        PageHelper.startPage(pageNum,30);
        //查询
        List<Role> result = authorityService.getAllRoleByQueryInfo(role);
        PageInfo<Role> pageInfo=new PageInfo<>(result);
        int total = (int) pageInfo.getTotal();
        return Msg.success()
                .add("result",pageInfo.getList())
                .add("total",total);
    }

    @GetMapping("loadMenuList")
    public Msg loadMenuList(){
        return Msg.success().add("menus",authorityService.getAllMenu());
    }

    @GetMapping("loadAllMenuDirect/{uid}")
    public Msg loadMenuByUid(@PathVariable Long uid){
        return Msg.success().add("menus_uid",authorityService.getMenuByUidDirect(uid));
    }

    @PutMapping("saveMenuByUid")
    public Msg saveMenuByUid(@RequestBody MenuUpdate menus){
        boolean b = false;
        try {
            b = authorityService.updateEmployAuthority(menus);
        }catch (Exception e){
            e.printStackTrace();
        }
        return b ?Msg.success():Msg.fail();
    }

    @DeleteMapping("role/{rid}")
    public Msg deleteRoleByRid(@PathVariable("rid") Integer rid){
        return authorityService.deleteRoleById(rid)?Msg.success():Msg.fail();
    }

    @PostMapping("role")
    public Msg addRoleBy(@RequestBody Role role){
        if (role.getRoleName() ==null|| role.getRoleVal() ==null){return Msg.fail();}
        return authorityService.addRole(role)?Msg.success():Msg.fail();
    }
    @PutMapping("role")
    public Msg updateRoleBy(@RequestBody Role role){
        return authorityService.update(role)?Msg.success():Msg.fail();
    }
    @PutMapping("roleChange")
    public Msg roleChange(@RequestBody Employ employ){
        Long uid = employ.getUid();
        Integer roleId = employ.getRoleId();
        if (uid ==null || roleId ==null){return Msg.fail();}
        return authorityService.updateEmployRole(uid,roleId)?Msg.success():Msg.fail();
    }
}
