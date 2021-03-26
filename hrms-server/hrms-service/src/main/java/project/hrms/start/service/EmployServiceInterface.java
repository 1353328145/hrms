package project.hrms.start.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import project.hrms.start.entity.Employ;

import java.util.Date;
import java.util.List;

public interface EmployServiceInterface {
    Employ getEmployByUid(Long uid);

    boolean save(Employ employ);

    List<Employ> getEmployForAuth(Employ employ);
}
