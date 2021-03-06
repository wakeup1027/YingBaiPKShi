package lxq.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bean.SecondTable;

public class SecondTimerJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		if(st.getInt("second")>0){
			if(st.getInt("second")<=st.getInt("closetime")){
				if(st.getStr("fenpan").equals("0")){
					st.set("close", "2");
				}else{
					st.set("close", "0");
				}
			}else{
				if(st.getStr("fenpan").equals("0")){
					st.set("close", "2");
				}else{
					st.set("close", "1");
				}
			}
			st.set("second", st.getInt("second")-1);
			st.update();
		}
	}
	
}
