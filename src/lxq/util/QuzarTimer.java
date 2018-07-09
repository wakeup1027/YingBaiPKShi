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
 * ����������
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
		//����scheduler
        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail QuzarTimerJob = newJob(QuzarTimerJob.class) //����Job��ΪQuartzJob�࣬����������ִ���߼�����
	                .withIdentity(QuzarTimerJobStr, QuzarTimerGroupStr) //����name/group
	                .build();

			Trigger TimeTrigger = newTrigger()
            	    .withIdentity(TimeTriggerStr, TimeTriggerWithIdentityStr)
            	    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
            	    		.withMisfireHandlingInstructionDoNothing())  //0 2 10 23 4 ? 2018
            	    .build();
			
			//�����������
            scheduler.scheduleJob(QuzarTimerJob, TimeTrigger);
            scheduler.start();
            
            //��ͣ����
            //scheduler.pauseJob(JobKey.jobKey(QuzarTimerJobStr, QuzarTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//���¿�����ʱ���ķ���
	public void starQuzar(){
        try {
        	scheduler.resumeJob(JobKey.jobKey(QuzarTimerJobStr, QuzarTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//��ͣ��ʱ���ķ���
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