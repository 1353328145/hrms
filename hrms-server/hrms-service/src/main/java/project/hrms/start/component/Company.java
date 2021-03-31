package project.hrms.start.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Company {
    //公司名称
    @Value("${company.name}")
    private String name;
    //公司成立日期
    @Value("${company.createDate}")
    private String createDate;
    //公司法定法定代表人
    @Value("${company.legal}")
    private String legal;
    //公司地址
    @Value("${company.address}")
    private String address;
    //公司简介
    @Value("${company.info}")
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
