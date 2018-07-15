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
		//����scheduler
        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail QuzarTimerJob1 = newJob(SecondTimerJob.class) //����Job��ΪQuartzJob�࣬����������ִ���߼�����
	                .withIdentity(SecondTimerJobStr, SecondTimerGroupStr) //����name/group
	                .build();

			Trigger TimeTrigger1 = newTrigger()
            	    .withIdentity(SecondTimeTriggerStr, SecondTimeTriggerWithIdentityStr)
            	    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())  //0 2 10 23 4 ? 2018
            	    .build();
			
			//�����������
            scheduler.scheduleJob(QuzarTimerJob1, TimeTrigger1);
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
        	scheduler.resumeJob(JobKey.jobKey(SecondTimerJobStr, SecondTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//��ͣ��ʱ���ķ���
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
