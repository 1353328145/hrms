package project.hrms.start.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import project.hrms.start.entity.Employ;

import java.io.InputStream;
import java.util.List;

public interface EmployServiceInterface {
    Employ getEmployByUid(Long uid);

    Long save(Employ employ);

    List<Employ> getEmployForAuth(Employ employ);

    int getCount();

    boolean update(Employ employ);

    boolean delete(Long uid);

    String uploadImg(InputStream inputStream, String contentType);

    boolean updateImgKey(Employ employ);
}
