package project.hrms.start.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * (Sign)实体类
 *
 */
public class Sign implements Serializable {
    private static final long serialVersionUID = 932192684702679903L;
    @TableId(value = "sid", type = IdType.ID_WORKER)
    private Long sid;

    private Long uid;

    private Date signIn;

    private String signInImg;

    private Date signOut;

    private String signOutImg;

    private Date createTime;

    private String status;

    @TableField(exist = false)
    private Employ employ;

    public Employ getEmploy() {
        return employ;
    }

    public void setEmploy(Employ employ) {
        this.employ = employ;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getSignIn() {
        return signIn;
    }

    public void setSignIn(Date signIn) {
        this.signIn = signIn;
    }

    public String getSignInImg() {
        return signInImg;
    }

    public void setSignInImg(String signInImg) {
        this.signInImg = signInImg;
    }

    public Date getSignOut() {
        return signOut;
    }

    public void setSignOut(Date signOut) {
        this.signOut = signOut;
    }

    public String getSignOutImg() {
        return signOutImg;
    }

    public void setSignOutImg(String signOutImg) {
        this.signOutImg = signOutImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "sid=" + sid +
                ", uid=" + uid +
                ", signIn=" + signIn +
                ", signInImg='" + signInImg + '\'' +
                ", signOut=" + signOut +
                ", signOutImg='" + signOutImg + '\'' +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                ", employ=" + employ +
                '}';
    }
}