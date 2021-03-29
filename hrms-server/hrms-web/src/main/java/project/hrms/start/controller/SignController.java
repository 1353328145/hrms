package project.hrms.start.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hrms.start.entity.Employ;
import project.hrms.start.parameter.Msg;
import project.hrms.start.service.EmployService;
import project.hrms.start.service.EmployServiceInterface;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("sign")
public class SignController {
    @Autowired
    private EmployServiceInterface employServiceInterface;
    @RequestMapping("index")
    public String index(){
        return "login";
    }
    @RequestMapping("login")
    public String Login(Employ employ, Model model, HttpSession session){
        Long uid = employ.getUid();
        String password = employ.getPassword();
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(uid.toString(), password));
        }catch (UnknownAccountException e){
            model.addAttribute("msg","工号错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
        session.setAttribute(uid.toString(),employServiceInterface.getEmployByUid(uid));
        return "index";
    }
}
