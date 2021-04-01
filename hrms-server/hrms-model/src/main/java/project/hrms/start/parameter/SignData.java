package project.hrms.start.parameter;

import java.util.List;

public class SignData {
    private String status;
    private Integer number;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "SignData{" +
                "status='" + status + '\'' +
                ", number=" + number +
                '}';
    }
}
