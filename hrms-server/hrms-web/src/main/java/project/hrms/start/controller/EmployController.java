package project.hrms.start.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jexing.cup.ImageStore;
import com.jexing.cup.exception.FileTypeNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.hrms.start.parameter.ChartData;
import project.hrms.start.parameter.Msg;
import project.hrms.start.entity.Employ;
import project.hrms.start.service.EmployService;
import project.hrms.start.service.EmployServiceInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("employ")
public class EmployController {
    @Autowired
    private EmployServiceInterface employService;

    @Autowired
    private ImageStore store;

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

    @GetMapping("uidTest/{uid}")
    public Msg uidTest(@PathVariable("uid")String s){
        Long uid ;
        try {
            uid = Long.parseLong(s);
        }catch (Exception e){
            return Msg.fail();
        }
        Employ employ =employService.getEmployByUid(uid);
        return employ == null?Msg.fail():Msg.success().add("name",employ.getName());
    }

    @GetMapping("findByUid/{uid}")
    public Msg findByUid(@PathVariable("uid")Long uid){
        Employ employ =employService.getEmployByUid(uid);
            return employ == null?Msg.fail():Msg.success().add("employ",employ);
    }

    @PutMapping("em")
    public Msg updateEmploy (@RequestBody Employ employ){
        return employService.update(employ)?Msg.success():Msg.fail();
    }

    @DeleteMapping("em/{uid}")
    public Msg deleteEmploy (@PathVariable("uid") Long uid){
        return employService.delete(uid)?Msg.success():Msg.fail();
    }

    @PostMapping("img")
    public Msg upload(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.fail();
        }
        String key = employService.uploadImg(inputStream,file.getContentType());
        return key == null? Msg.fail():Msg.success().add("key",key);
    }
    @PutMapping("updateImgKey")
    public Msg updateImgKey(@RequestBody Employ employ){
        return employService.updateImgKey(employ)?Msg.success():Msg.fail();
    }
    @GetMapping("getImg")
    public Msg getImg(String key){
        return key == null?Msg.fail():Msg.success().add("img",store.getObjForBase64(key));
    }

    @GetMapping(value = "img",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImgForByte(String key){
        return store.getObjForByte(key);
    }
    @GetMapping("CountGroupByCulture")
    public Msg getCountGroupByCulture() {
        return Msg.success().add("info",employService.getCountGroupByCulture());
    }
}
