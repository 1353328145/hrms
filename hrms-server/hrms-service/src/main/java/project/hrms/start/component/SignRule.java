package project.hrms.start.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@PropertySource("classpath:time.properties")
public class SignRule {
    @Value("${signRule.in}")
    private String in;
    @Value("${signRule.out}")
    private String out;
    @Value("${signRule.free}")
    private String free;
    private SimpleDateFormat ymdhm;
    private SimpleDateFormat ymd;
    public SignRule(){
        ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ymd = new SimpleDateFormat("yyyy-MM-dd");
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
    public Date getSignInTime(){
        String format = ymd.format(new Date());
        Date date = null;
        try {
            date = ymdhm.parse(format + " " +this.in);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getSignOutTime(){
        String format = ymd.format(new Date());
        Date date = null;
        try {
            date = ymdhm.parse(format + " " +this.out);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public int[] getFreeArray(){
        int []answer =new int[free.length()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = free.charAt(i) - '0';
        }
        return answer;
    }
}
