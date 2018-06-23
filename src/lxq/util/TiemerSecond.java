package lxq.util;

import java.util.Timer;
import java.util.TimerTask;

import com.base.BaseController;
import com.bean.TaskTimerBean;
import com.bean.TimeNumOver;

public class TiemerSecond extends BaseController{

	public static void timer1() {
		//设置定时器的状态为启动状态
		final Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	      public void run() {
	    	  TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
				if(taskt.getInt("status")==0){//表示开奖器是关闭的，所以计时器也要关闭
					System.out.println("我结束了计时器！");
					TimeNumOver tasktover = TimeNumOver.dao.findById(1);
					tasktover.set("number", -1);
					tasktover.update();
					timer.cancel();
				}else{
					//先找数据库中的倒数时间
					TimeNumOver nowNum = TimeNumOver.dao.findById(1);
					//如果小于1S那就重新赋值到600S
					if(nowNum.getInt("number")<=1){
						nowNum.set("number", 600);
					}else{//否则减去2S
						nowNum.set("number", nowNum.getInt("number")-2);
					}
					nowNum.update();
				}
	      }
	      
	    }, 2000, 2000);
	}
	
}
