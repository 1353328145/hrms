package project.hrms.start.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jexing.cup.ImageStore;
import com.jexing.cup.exception.FileTypeNotSupportedException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Employ;
import project.hrms.start.mapper.DepartmentMapper;
import project.hrms.start.mapper.EmployMapper;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service("employService")
public class EmployService implements EmployServiceInterface{
    @Autowired
    EmployMapper employMapper;

    @Autowired
    ImageStore store;

    @Autowired
    DepartmentMapper departmentMapper;

    public Employ getEmployByUid(Long uid){
        return employMapper.getOneByUid(uid);
    }

    public Long save(Employ employ) {
        employ.setPassword(new Md5Hash("111111").toHex());
        employ.setCreateTime(new Date());
        employMapper.insert(employ);
        return employ.getUid();
    }

    public List<Employ> getEmployForAuth(Employ employ) {
        return employMapper.getAllByInfo(employ);
    }

    @Override
    public int getCount() {
        return employMapper.selectCount(null);
    }

    @Override
    public boolean update(Employ employ) {
        if (employ.getUid() == null){return false;}
        if (employ.getPassword()!=null){
            employ.setPassword(new Md5Hash(employ.getPassword()).toHex());
        }
        return employMapper.updateById(employ)>0;
    }

    @Override
    public boolean delete(Long uid) {
        if (uid == null ||departmentMapper.selectCount(new QueryWrapper<Department>().eq("admin_employ",uid)) != 0){
            return false;
        }
        return employMapper.deleteById(uid)>0;
    }

    @Override
    public String uploadImg(InputStream inputStream, String contentType) {
        String key = null;
        try {
            key = store.putObj(inputStream,contentType);
        } catch (FileTypeNotSupportedException ignored) {}
        return key;
    }

    @Override
    public boolean updateImgKey(Employ employ) {
        if (employ.getUid() ==null||employ.getImgKey() == null){return false;}
        Employ e = employMapper.getOneByUid(employ.getUid());
        if (e == null){return false;}
        String imgKey = e.getImgKey();
        if (imgKey != null && store.contain(imgKey)){
            store.delete(imgKey);
        }
        return employMapper.updateById(employ)>0;
    }
}
