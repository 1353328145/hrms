package project.hrms.start.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hrms.start.entity.Employ;
import project.hrms.start.entity.EmployMenu;
import project.hrms.start.entity.Menu;
import project.hrms.start.entity.Role;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.mapper.EmployMenuMapper;
import project.hrms.start.mapper.MenuMapper;
import project.hrms.start.mapper.RoleMapper;
import project.hrms.start.parameter.MenuUpdate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityService implements AuthorityServiceInterface{
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

    public List<Role> getAllRoleByQueryInfo(Role role) {
        return roleMapper.getAllByInfo(role);
    }

    public List<Menu> getMenuByUidDirect(Long uid) {
        return menuMapper.getMenuByUid(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateEmployAuthority(MenuUpdate menuUpdate){
        Long uid = menuUpdate.getUid();
        List<Menu> menus = menuUpdate.getMenus();
        if (uid == null){
            return false;
        }
        employMenuMapper.deleteByUidInt(uid);
        for (Menu item : menus) {
            Integer mid = item.getMid();
            if (mid==null){
                throw new RuntimeException();
            }
            employMenuMapper.insert(uid,mid);
        }
        return true;
    }
}
