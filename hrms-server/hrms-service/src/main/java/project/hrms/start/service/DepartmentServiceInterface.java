package project.hrms.start.service;

import project.hrms.start.entity.Department;
import project.hrms.start.parameter.ChartData;

import java.util.List;
import java.util.Map;

public interface DepartmentServiceInterface {
    List<Department> getDepartmentByInfo(Department department);
    boolean updateDepartmentByDid(Department department);
    boolean deleteByDid(Integer did);
    boolean insert(Department department);

    List<ChartData> getEmployInfoByDp();

    int getCount();
}
