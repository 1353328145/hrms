package project.hrms.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hrms.start.entity.Department;
import project.hrms.start.entity.Employ;
import project.hrms.start.mapper.DepartmentMapper;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.parameter.ChartData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService implements DepartmentServiceInterface{
    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployMapper employMapper;

    @Override
    public List<Department> getDepartmentByInfo(Department department) {
        return departmentMapper.getDepartmentByInfo(department);
    }

    @Override
    public boolean updateDepartmentByDid(Department department) {
        if (department.getDid() == null){return false;}
        return departmentMapper.updateById(department) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByDid(Integer did) {
        employMapper.setDepartmentToNullByDid(did);
        return departmentMapper.deleteById(did)>0;
    }

    @Override
    public boolean insert(Department department) {
        return departmentMapper.insert(department) > 0;
    }

    @Override
    public List<ChartData> getEmployInfoByDp() {

        return employMapper.getCountGroupByDid();
    }

    @Override
    public int getCount() {
        return departmentMapper.selectCount(null);
    }

    @Override
    public List<Department> getAll() {
        return departmentMapper.getAll();
    }
}
