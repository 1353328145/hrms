package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Role;
import project.hrms.start.parameter.ChartData;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    Role getRoleByRid(Integer rid);

    List<Role> getAllByInfo(Role role);

    int update(Role role);
}
