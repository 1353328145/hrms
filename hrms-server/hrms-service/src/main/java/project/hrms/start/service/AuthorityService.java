package project.hrms.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.entity.Menu;
import project.hrms.start.mapper.MenuMapper;

import java.util.List;

@Service
public class AuthorityService {
    @Autowired
    MenuMapper menuMapper;

    /**
     * 返回所有菜单
     * @return
     */
    public List<Menu> getAllMenu(){
        return menuMapper.selectList(null);
    }
}
