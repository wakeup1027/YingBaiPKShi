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
		System.out.println("1sִ��һ�Σ�"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/�´�ִ�У�"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(execute.getNextFireTime()));
		//�������ݿ��еĵ���ʱ��
		TimeNumOver nowNum = TimeNumOver.dao.findById(1);
		//���С��1S�Ǿ����¸�ֵ��600S
		if(nowNum.getInt("number")<=1){
			nowNum.set("number", 600);
		}else{//�����ȥ1S
			nowNum.set("number", nowNum.getInt("number")-1);
		}
		nowNum.update();
	}

}
*/