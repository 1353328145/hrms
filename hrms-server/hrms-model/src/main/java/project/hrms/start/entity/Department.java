package project.hrms.start.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * (Department)实体类
 *
 */
public class Department implements Serializable {
    private static final long serialVersionUID = -86313127857613869L;

    @TableId(type = IdType.AUTO)
    private Integer did;

    private String name;

    private Long adminEmploy;

    private String description;

    private Integer pdid;

    private Employ admin;

    public Employ getAdmin() {
        return admin;
    }

    public void setAdmin(Employ admin) {
        this.admin = admin;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAdminEmploy() {
        return adminEmploy;
    }

    public void setAdminEmploy(Long adminEmploy) {
        this.adminEmploy = adminEmploy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPdid() {
        return pdid;
    }

    public void setPdid(Integer pdid) {
        this.pdid = pdid;
    }

    @Override
    public String toString() {
        return "Department{" +
                "did=" + did +
                ", name='" + name + '\'' +
                ", adminEmploy=" + adminEmploy +
                ", description='" + description + '\'' +
                ", pdid=" + pdid +
                ", admin=" + admin +
                '}';
    }
}
