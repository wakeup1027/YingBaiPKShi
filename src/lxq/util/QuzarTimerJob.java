/*package lxq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuzarTimerJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("工作开始（当前时间）"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/下次执行："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getNextFireTime()));
		//执行完这个任务之后执行计数器任务
		QuzarWebTimer.getInstance().starQuzar();
	}

}
*/