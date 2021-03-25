package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Menu;

@Repository

public interface MenuMapper extends BaseMapper<Menu> {
}
