package project.hrms.start.entity;

import java.io.Serializable;

/**
 * (EmployMenu)实体类
 *
 */
public class EmployMenu implements Serializable {
    private static final long serialVersionUID = 909013649471106126L;

    private Integer mid;

    private Integer uid;


    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}