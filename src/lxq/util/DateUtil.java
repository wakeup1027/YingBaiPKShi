package lxq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public String getTime(String dateStr,int time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		try {
			now = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(now);
		nowTime.add(Calendar.MINUTE, time);
		return sdf.format(nowTime.getTime());
	}
	
	public static void main(String[] args) {
		System.out.println(new DateUtil().getTime("2017-05-05 00:00:00",10));
	}
}
