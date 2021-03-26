package project.hrms.start.service;

import project.hrms.start.entity.Menu;
import project.hrms.start.entity.Role;
import project.hrms.start.parameter.MenuUpdate;

import java.util.List;

public interface AuthorityServiceInterface {
     List<Menu> getAllMenu();
     List<Menu> getMenuByUid(Long uid);
     List<Role> getAllRole();
     List<Role> getAllRoleByQueryInfo(Role role);
     List<Menu> getMenuByUidDirect(Long uid);
     boolean updateEmployAuthority(MenuUpdate menuUpdate);
}
