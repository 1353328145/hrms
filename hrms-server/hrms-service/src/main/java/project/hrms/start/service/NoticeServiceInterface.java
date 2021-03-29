package project.hrms.start.service;

import project.hrms.start.entity.Notice;

import java.util.List;

public interface NoticeServiceInterface {
    List<Notice> getAllByTitle(Notice notice);
    boolean delete(Integer id);
    boolean update(Notice notice);
    boolean insert(Notice notice);
}
