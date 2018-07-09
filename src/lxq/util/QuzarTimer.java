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

/**
 * 开奖器的类
 * @author Administrator
 *
 */

public class QuzarTimer {
	private Scheduler scheduler = null;
	private static QuzarTimer instance = new QuzarTimer();
	private static final String QuzarTimerJobStr = "QuzarTimerJob";
	private static final String QuzarTimerGroupStr = "QuzarTimerGroup";
	private static final String TimeTriggerStr = "TimeTrigger";
	private static final String TimeTriggerWithIdentityStr = "TimeTriggerWithIdentity";
	
	/*private QuzarTimer(){
		Quzar();
	}*/
	
	public void Quzar(){
		//创建scheduler
        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail QuzarTimerJob = newJob(QuzarTimerJob.class) //定义Job类为QuartzJob类，这是真正的执行逻辑所在
	                .withIdentity(QuzarTimerJobStr, QuzarTimerGroupStr) //定义name/group
	                .build();

			Trigger TimeTrigger = newTrigger()
            	    .withIdentity(TimeTriggerStr, TimeTriggerWithIdentityStr)
            	    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
            	    		.withMisfireHandlingInstructionDoNothing())  //0 2 10 23 4 ? 2018
            	    .build();
			
			//加入这个调度
            scheduler.scheduleJob(QuzarTimerJob, TimeTrigger);
            scheduler.start();
            
            //暂停任务
            //scheduler.pauseJob(JobKey.jobKey(QuzarTimerJobStr, QuzarTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//重新开启计时器的方法
	public void starQuzar(){
        try {
        	scheduler.resumeJob(JobKey.jobKey(QuzarTimerJobStr, QuzarTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//暂停计时器的方法
	public void stopQuzar(){
        try {
        	scheduler.pauseJob(JobKey.jobKey(QuzarTimerJobStr, QuzarTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static QuzarTimer getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		QuzarTimer qzt = getInstance();
		qzt.starQuzar();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qzt.stopQuzar();
	}
	
}