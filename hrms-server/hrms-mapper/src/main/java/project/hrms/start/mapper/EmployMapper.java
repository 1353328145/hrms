package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Employ;

@Repository
public interface EmployMapper extends BaseMapper<Employ> {

    Employ getOneByUid(Long uid);
}
