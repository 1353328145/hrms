package project.hrms.start.parameter;

import project.hrms.start.entity.Menu;

import java.util.List;

public class MenuUpdate {
    private List<Menu> menus;
    private Long uid;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "MenuUpdate{" +
                "menus=" + menus +
                ", uid=" + uid +
                '}';
    }
}
