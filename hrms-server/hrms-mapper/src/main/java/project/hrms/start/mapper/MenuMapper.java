package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Menu;

import java.util.List;

@Repository

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenuByUid(Long uid);
}
