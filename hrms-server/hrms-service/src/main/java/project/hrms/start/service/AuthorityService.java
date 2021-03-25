package project.hrms.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.entity.Employ;
import project.hrms.start.entity.Menu;
import project.hrms.start.entity.Role;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.mapper.EmployMenuMapper;
import project.hrms.start.mapper.MenuMapper;
import project.hrms.start.mapper.RoleMapper;

import java.util.List;

@Service
public class AuthorityService {
    public static final int EMPLOY = 1;
    public static final int ADMIN = 2;
    public static final int SUPER_ADMIN = 3;
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    EmployMenuMapper employMenuMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    EmployMapper employMapper;
    /**
     * 返回所有菜单
     * @return
     */
    public List<Menu> getAllMenu(){
        return menuMapper.selectList(null);
    }
    public List<Menu> getMenuByUid(Long uid){
        List<Menu> list = null;
        Employ employ =employMapper.getOneByUid(uid);
        if (employ==null || employ.getRole()==null || employ.getRole().getRoleVal() ==null){
            return null;
        }
        int roleVal = employ.getRole().getRoleVal();
        switch (roleVal){
            case ADMIN: list = menuMapper.getMenuByUid(uid);
                break;
            case SUPER_ADMIN: list = getAllMenu();
                break;
            default: break;
        }
        return list;
    }

    public List<Role> getAllRole() {
        return roleMapper.selectList(null);
    }
}
