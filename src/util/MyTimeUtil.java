package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MyTimeUtil {
    public static Timestamp getZeroTime(){
        Timestamp timestamp=null;
        String date = "0000-00-00T00:00"; // <input type="datetime-local"> 输入参数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date dt = sdf.parse(date);
            timestamp=new Timestamp(dt.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    public static Timestamp getCurrentTime(){
        long currentTimeMillions=System.currentTimeMillis();
        Timestamp timestamp=new Timestamp(currentTimeMillions);
        return timestamp;
    }
    public static String timeChange(String s){
        Date dt = new Date(Long.parseLong(s));//获取后台传来的时间并转换为长整形数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(dt);
    }
}
