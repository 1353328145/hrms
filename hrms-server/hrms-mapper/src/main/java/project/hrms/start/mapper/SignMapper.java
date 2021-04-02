package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Sign;
import project.hrms.start.parameter.SignData;

import java.util.List;

@Repository
public interface SignMapper extends BaseMapper<Sign> {
    //查询某用户某天的签到记录
    List<Sign> getSignInfoByUid(Sign sign);
    //查询某用户本月的签到记录次数汇总
    List<SignData> getSignInfoByUidGroupByStatus(Long uid,int[] free);

    Integer countTodaySignInNumber();

    List<Sign> getAllByUidAndMonth(Sign sign);
}
