package project.hrms.start.service;

import project.hrms.start.entity.Sign;

import java.util.List;

/**
 * 时间格式
 * 实例 2020-3-31
 */
public interface SignServiceInterface {
    boolean sign(Sign sign,String key);

    //获取今天的签到表
    Sign getSignInfoByUid(Long uid);
    //获取所有签到表，时间查询
    List<Sign> getSignInfoByUid(Long uid,String time);
}
