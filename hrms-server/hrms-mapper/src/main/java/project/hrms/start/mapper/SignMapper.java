package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Sign;

import java.util.List;

@Repository
public interface SignMapper extends BaseMapper<Sign> {
    List<Sign> getSignInfoByUid(Sign sign);

}
