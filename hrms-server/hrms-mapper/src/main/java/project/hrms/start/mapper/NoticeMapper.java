package project.hrms.start.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import project.hrms.start.entity.Notice;

import java.util.List;

@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> getAllByTitle(Notice notice);

    Notice getOneRecent();
}
