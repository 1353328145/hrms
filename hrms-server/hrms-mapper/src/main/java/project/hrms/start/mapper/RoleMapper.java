package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Role;
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
