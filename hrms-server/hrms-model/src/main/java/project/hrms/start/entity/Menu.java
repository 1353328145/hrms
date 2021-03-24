package project.hrms.start.entity;


import java.io.Serializable;

/**
 * (Menu)实体类
 *
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = -70819686193920319L;

    private Integer mid;

    private String info;

    private String menuPath;


    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

}