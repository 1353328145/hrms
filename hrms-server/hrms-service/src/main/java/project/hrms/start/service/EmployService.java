package project.hrms.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.entity.Employ;
import project.hrms.start.mapper.EmployMapper;

@Service
public class EmployService {
    @Autowired
    EmployMapper employMapper;

    public Employ getEmployByUidPassword(){
        return null;
    }
}
