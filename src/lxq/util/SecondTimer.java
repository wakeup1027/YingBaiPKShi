package lxq.util;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SecondTimer {
	private Scheduler scheduler = null;
	private static SecondTimer instance = new SecondTimer();
	private static final String SecondTimerJobStr = "SecondTimerJob";
	private static final String SecondTimerGroupStr = "SecondTimerGroup";
	private static final String SecondTimeTriggerStr = "SecondTimeTrigger";
	private static final String SecondTimeTriggerWithIdentityStr = "SecondTimeTriggerWithIdentity";
	
	public SecondTimer(){
		Quzar();
	}
	
	public void Quzar(){
		//创建scheduler
        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail QuzarTimerJob1 = newJob(SecondTimerJob.class) //定义Job类为QuartzJob类，这是真正的执行逻辑所在
	                .withIdentity(SecondTimerJobStr, SecondTimerGroupStr) //定义name/group
	                .build();

			Trigger TimeTrigger1 = newTrigger()
            	    .withIdentity(SecondTimeTriggerStr, SecondTimeTriggerWithIdentityStr)
            	    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())  //0 2 10 23 4 ? 2018
            	    .build();
			
			//加入这个调度
            scheduler.scheduleJob(QuzarTimerJob1, TimeTrigger1);
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
        	scheduler.resumeJob(JobKey.jobKey(SecondTimerJobStr, SecondTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//暂停计时器的方法
	public void stopQuzar(){
        try {
        	scheduler.pauseJob(JobKey.jobKey(SecondTimerJobStr, SecondTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static SecondTimer getInstance() {
		return instance;
	}
	
	/*public static void main(String[] args) {
		SecondTimer.instance.Quzar();
	}*/
	
}
