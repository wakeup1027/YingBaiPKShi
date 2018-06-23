package lxq.util;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bean.TimeNumOver;

public class QuzarWebTimer {
	private Scheduler scheduler = null;
	private static QuzarWebTimer instance = new QuzarWebTimer();
	private static final String QuzarWebNumTimerJobStr = "QuzarWebNumTimerJob";
	private static final String QuzarWebNumTimerGroupStr = "QuzarWebNumTimerGroup";
	private static final String webTriggerStr = "webTrigger";
	private static final String webTriggerWithIdentityStr = "webTriggerWithIdentity";
	
	private QuzarWebTimer(){
		Quzar();
	}
	
	public void Quzar(){
		//创建scheduler
        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail QuzarWebNumTimerJob = newJob(QuzarWebJob.class) //定义Job类为QuartzJob类，这是真正的执行逻辑所在
	                .withIdentity(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr) //定义name/group
	                .build();
			
			Trigger webTrigger = newTrigger()
					//.startAt(openDate)
					.withIdentity(webTriggerStr, webTriggerWithIdentityStr)
					.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")
					    	.withMisfireHandlingInstructionDoNothing())  //0 2 10 23 4 ? 2018
					.build();
			
			//加入这个调度
            scheduler.scheduleJob(QuzarWebNumTimerJob, webTrigger);//计时器1s中执行一次的计时器
            scheduler.start();
            
            scheduler.pauseJob(JobKey.jobKey(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//重新开启计时器的方法
	public void starQuzar(){
        try {
        	scheduler.resumeJob(JobKey.jobKey(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//暂停计时器的方法
	public void stopQuzar(){
        try {
        	scheduler.pauseJob(JobKey.jobKey(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr));
        	//当开奖器暂停的时候，计数器也暂停的同时也要重置成600s
        	TimeNumOver nowNum = TimeNumOver.dao.findById(1);
        	nowNum.set("number", 600);
        	nowNum.update();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static QuzarWebTimer getInstance() {
		return instance;
	}
}
