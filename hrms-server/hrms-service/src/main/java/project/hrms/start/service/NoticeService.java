package project.hrms.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.entity.Notice;
import project.hrms.start.mapper.NoticeMapper;

import java.util.Date;
import java.util.List;

@Service
public class NoticeService implements NoticeServiceInterface{
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public List<Notice> getAllByTitle(Notice notice) {
        return noticeMapper.getAllByTitle(notice);
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null){return false;}
        return noticeMapper.deleteById(id)>0;
    }

    @Override
    public boolean update(Notice notice) {
        if (notice.getNid() == null){return false;}
        return noticeMapper.updateById(notice) > 0;
    }

    @Override
    public boolean insert(Notice notice) {
        notice.setCreateTime(new Date());
        return noticeMapper.insert(notice) > 0;
    }

    @Override
    public Notice getOne() {
        return noticeMapper.getOneRecent();
    }
}
