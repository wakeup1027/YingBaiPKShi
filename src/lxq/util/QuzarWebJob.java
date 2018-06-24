/*package lxq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bean.TimeNumOver;

public class QuzarWebJob implements Job{

	@Override
	public void execute(JobExecutionContext execute) throws JobExecutionException {
		System.out.println("1s执行一次："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/下次执行："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(execute.getNextFireTime()));
		//先找数据库中的倒数时间
		TimeNumOver nowNum = TimeNumOver.dao.findById(1);
		//如果小于1S那就重新赋值到600S
		if(nowNum.getInt("number")<=1){
			nowNum.set("number", 600);
		}else{//否则减去1S
			nowNum.set("number", nowNum.getInt("number")-1);
		}
		nowNum.update();
	}

}
*/