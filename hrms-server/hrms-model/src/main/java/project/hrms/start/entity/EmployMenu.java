package project.hrms.start.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * (EmployMenu)实体类
 *
 */
public class EmployMenu implements Serializable {
    private static final long serialVersionUID = 909013649471106126L;
    @TableId(type = IdType.AUTO)
    private Integer mid;
    @TableId(type = IdType.AUTO)
    private Long uid;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

}
