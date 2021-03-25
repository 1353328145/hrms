package project.hrms.start.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.hrms.start.bo.Msg;
import project.hrms.start.entity.Employ;
import project.hrms.start.service.AuthorityService;
import project.hrms.start.service.EmployService;
import project.hrms.start.util.TokenUtil;

@RestController
@RequestMapping("authority")
public class AuthorityController {
    @Autowired
    private EmployService employService;

    @Autowired
    private AuthorityService authorityService;
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
        boolean flag;
        try {
            flag = employService.save(employ);
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail();
        }
        return flag?Msg.success():Msg.fail();
    }

    @GetMapping("loadAllMenu")
    public Msg loadAllMenu(){
        return Msg.success().add("menus",authorityService.getAllMenu());
    }
}
