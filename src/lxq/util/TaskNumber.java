package lxq.util;

import java.util.Timer;
import java.util.TimerTask;

import com.base.BaseController;
import com.bean.TaskTimerBean;
import com.bean.TimeLong;
import com.bean.TimeNumOver;

public class TaskNumber extends BaseController{
	final Timer timer = new Timer();
	public void startTask() {
		Task uTask = new Task();
		timer.schedule(uTask, 1000, 1000);
	}
	
	public boolean stopTimer(){ //关闭的时候把倒计时设置为0
		System.out.println("我结束倒计时的功能");
		timer.cancel();
		TimeNumOver taskt = TimeNumOver.dao.findById(1);
		taskt.set("number", 0);
		taskt.update();
		return true;
	}
	
	private class Task extends TimerTask {
		public Task() {
			System.out.println("倒计时计时器开始");
			TimeLong tl = TimeLong.dao.findById(4);
			TimeNumOver taskt = new TimeNumOver();
			taskt.set("id", 1);
			taskt.set("number", tl.getInt("timelong"));
			taskt.update();
		}

		@Override
		public void run() {
			//OpenNum on = OpenNum.dao.findById(1);//查找一天开奖的期数
			//if(on.getInt("nowNum")<=on.getInt("openNum")){
				//判断计时器是否结束掉，而且结束的方法必须写在执行run任务结束之后。
				TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
				if(taskt.getInt("status")==0){
					TimeNumOver tasktover = TimeNumOver.dao.findById(1);
					tasktover.set("number", -1);
					tasktover.update();
					timer.cancel();
				}else{
					TimeNumOver nowNum = TimeNumOver.dao.findById(1);
					if(nowNum.getInt("number")<=1){
						TimeLong taskTimer = TimeLong.dao.findById(4);
						nowNum.set("number", taskTimer.getInt("timelong"));
					}else{
						nowNum.set("number", nowNum.getInt("number")-1);
					}
					nowNum.update();
				}
			/*}else{ //如果已开的期数大于规定的期数则应该是开完了
				System.out.println("超过今天规定的期数，我这个计数器作用的计时器也该结束了");
				TimeNumOver tasktover = TimeNumOver.dao.findById(1);
				tasktover.set("number", -1);
				tasktover.update();
				timer.cancel();
			}*/
			
		}
		
	}
	
}
