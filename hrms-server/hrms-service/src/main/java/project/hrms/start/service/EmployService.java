package project.hrms.start.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.entity.Employ;
import project.hrms.start.mapper.EmployMapper;

import java.util.Date;

@Service("employService")
public class EmployService {
    @Autowired
    EmployMapper employMapper;

    public Employ getEmployByUid(Long uid){
        return employMapper.selectOne(new QueryWrapper<Employ>().eq("uid",uid));
    }

    public boolean save(Employ employ) {
        employ.setPassword(new Md5Hash(employ.getPassword()).toHex());
        employ.setCreateTime(new Date());
        employMapper.insert(employ);
        System.out.println(employ);
        return true;
    }
}
