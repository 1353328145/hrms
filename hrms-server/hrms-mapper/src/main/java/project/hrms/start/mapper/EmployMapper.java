package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Employ;
import project.hrms.start.parameter.ChartData;

import java.util.List;

@Repository
public interface EmployMapper extends BaseMapper<Employ> {

    Employ getOneByUid(Long uid);

    List<Employ> getAllByInfo(Employ employ);

    int getNumberByRid(Integer rid);

    int updateRoleId(Long uid, Integer rid);

    void setDepartmentToNullByDid(Integer did);

    List<ChartData> getCountGroupByDid();


}
