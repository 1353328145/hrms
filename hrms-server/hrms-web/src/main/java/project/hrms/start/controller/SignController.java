package project.hrms.start.controller;

import com.jexing.cup.ImageStore;
import com.jexing.cup.exception.FileTypeNotSupportedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.hrms.start.entity.Employ;
import project.hrms.start.entity.Sign;
import project.hrms.start.parameter.Msg;
import project.hrms.start.service.EmployServiceInterface;
import project.hrms.start.service.SignServiceInterface;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("sign")
public class SignController {

    @Autowired
    private EmployServiceInterface employServiceInterface;

    @Autowired
    private SignServiceInterface signService;
    @Autowired
    private ImageStore store;
    @RequestMapping()
    public String index(){
        return "login";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String Login(Employ employ, Model model, HttpSession session){
        Long uid = employ.getUid();
        String password = employ.getPassword();
        if (uid == null || password == null){
            return "login";
        }
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
        Employ crrent = employServiceInterface.getEmployByUid(uid);
        session.setAttribute("employ",crrent);
        model.addAttribute("employ",crrent);
        return "index";
    }

    @PostMapping("file")
    @ResponseBody
    public Msg file(MultipartFile file){
        String key = null;
        try {
            key =store.putObj(file.getInputStream(),file.getContentType());
        } catch (FileTypeNotSupportedException | IOException e) {
            e.printStackTrace();
        }
        return key == null? Msg.fail():Msg.success().add("key",key);
    }
    @PostMapping("toSign")
    @ResponseBody
    public Msg toSign(Long uid,String key){
        if (uid==null||key==null){
            return Msg.fail();
        }
        Sign sign = new Sign();
        sign.setUid(uid);
        return signService.sign(sign,key)?Msg.success():Msg.fail();
    }

    @GetMapping("findByUid/{uid}")
    @ResponseBody
    public Msg findByUid(@PathVariable("uid")Long uid){
        Employ employ =employServiceInterface.getEmployByUid(uid);
        return employ == null?Msg.fail():Msg.success().add("employ",employ);
    }

}
