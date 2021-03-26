package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.EmployMenu;
@Repository
public interface EmployMenuMapper extends BaseMapper<EmployMenu> {
    int deleteByUidInt(Long uid);
    int insert(Long uid,Integer mid);
}
