/*package lxq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuzarTimerJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("������ʼ����ǰʱ�䣩"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/�´�ִ�У�"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getNextFireTime()));
		//ִ�����������֮��ִ�м���������
		QuzarWebTimer.getInstance().starQuzar();
	}

}
*/