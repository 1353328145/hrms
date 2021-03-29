package project.hrms.start.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    @TableId(value = "nid", type = IdType.AUTO)
    private Integer nid;
    private Long uid;
    private String title;
    private String content;
    private Date createTime;
    @TableField(exist = false)
    private Employ employ;
    @TableField(exist = false)
    private String employRoleName;

    public String getEmployRoleName() {
        return employRoleName;
    }

    public void setEmployRoleName(String employRoleName) {
        this.employRoleName = employRoleName;
    }

    public Employ getEmploy() {
        return employ;
    }

    public void setEmploy(Employ employ) {
        this.employ = employ;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "nid=" + nid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", employ=" + employ +
                ", employRoleName='" + employRoleName + '\'' +
                '}';
    }
}
