package project.hrms.start.service;

import project.hrms.start.entity.Menu;
import project.hrms.start.entity.Role;
import project.hrms.start.parameter.MenuUpdate;

import java.util.List;

public interface AuthorityServiceInterface {
     List<Menu> getAllMenu();
     List<Menu> getMenuByUid(Long uid);
     List<Menu> getMenuByUidDirect(Long uid);

     List<Role> getAllRole();
     List<Role> getAllRoleByQueryInfo(Role role);
     boolean deleteRoleById(Integer rid);
     boolean addRole(Role role);
     boolean update(Role role);

     boolean updateEmployAuthority(MenuUpdate menuUpdate);
     boolean updateEmployRole(Long uid,Integer rid);
}
