package project.hrms.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hrms.start.component.SignRule;
import project.hrms.start.entity.Sign;
import project.hrms.start.mapper.SignMapper;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SignService implements SignServiceInterface{
    @Autowired
    private SignMapper signMapper;

    @Autowired
    private SignRule signRule;
    private SimpleDateFormat ymd;
    public SignService(){
        ymd = new SimpleDateFormat("yyyy-MM-dd");
    }
    @Override
    public boolean sign(Sign sign,String key) {
        Sign toDay = getSignInfoByUid(sign.getUid());
        if (toDay == null){
            //今天还没有记录
            sign.setSignInImg(key);
            return signIn(sign);
        }else if (toDay.getSignIn()!=null&&toDay.getSignOut()==null){
            //签退
            sign.setSignOutImg(key);
            return signOut(toDay);
        }else{
            return false;
        }
    }


    private boolean signIn(Sign sign) {
        //签到时间
        Date signIn = new Date();
        long currentTime = signIn.getTime();
        sign.setSignIn(signIn);
        sign.setCreateTime(signIn);
        long time = signRule.getSignInTime().getTime();
        if (currentTime > time){
            if (currentTime - time > 7200000){ //两小时
                sign.setStatus("旷工");
            }else{
                sign.setStatus("迟到#未签退");
            }
        }else{
            sign.setStatus("未签退");
        }
        return signMapper.insert(sign) > 0;
    }

    private boolean signOut(Sign sign) {
        //签到时间
        Date signOut = new Date();
        sign.setSignOut(signOut);
        if (sign.getStatus().equals("未签退")){
            sign.setStatus("正常");
        }
        if (sign.getStatus().contains("迟到")){
            sign.setStatus("迟到");
        }
        return signMapper.updateById(sign) > 0;
    }

    @Override
    public Sign getSignInfoByUid(Long uid) {
        Sign sign = new Sign();
        sign.setUid(uid);

        sign.setCreateTime(getDate());
        List<Sign> list = signMapper.getSignInfoByUid(sign);
        return list.size()==0?null:list.get(0);
    }

    @Override
    public List<Sign> getSignInfoByUid(Long uid, String time) {
        Date date;
        try {
            date = ymd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Sign sign = new Sign();
        sign.setUid(uid);
        sign.setCreateTime(date);
        return signMapper.getSignInfoByUid(sign);
    }

    private Date getDate(){
        Date date = null;
        try {
            date = ymd.parse(ymd.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
