package project.hrms.start;


import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTest {

    @Test
    public void test(){
        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar);
        SimpleDateFormat dateFormat =new SimpleDateFormat();
        String format = dateFormat.format(new Date());
        System.out.println(format);
    }
}
