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
		//����scheduler
        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail QuzarWebNumTimerJob = newJob(QuzarWebJob.class) //����Job��ΪQuartzJob�࣬����������ִ���߼�����
	                .withIdentity(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr) //����name/group
	                .build();
			
			Trigger webTrigger = newTrigger()
					//.startAt(openDate)
					.withIdentity(webTriggerStr, webTriggerWithIdentityStr)
					.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")
					    	.withMisfireHandlingInstructionDoNothing())  //0 2 10 23 4 ? 2018
					.build();
			
			//�����������
            scheduler.scheduleJob(QuzarWebNumTimerJob, webTrigger);//��ʱ��1s��ִ��һ�εļ�ʱ��
            scheduler.start();
            
            scheduler.pauseJob(JobKey.jobKey(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//���¿�����ʱ���ķ���
	public void starQuzar(){
        try {
        	scheduler.resumeJob(JobKey.jobKey(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//��ͣ��ʱ���ķ���
	public void stopQuzar(){
        try {
        	scheduler.pauseJob(JobKey.jobKey(QuzarWebNumTimerJobStr, QuzarWebNumTimerGroupStr));
        	//����������ͣ��ʱ�򣬼�����Ҳ��ͣ��ͬʱҲҪ���ó�600s
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
