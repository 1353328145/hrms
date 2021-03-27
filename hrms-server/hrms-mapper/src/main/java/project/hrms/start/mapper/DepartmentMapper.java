package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Department;

import java.util.List;

@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getDepartmentByInfo(Department department);
}
