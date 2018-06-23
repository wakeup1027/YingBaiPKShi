package lxq.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.base.BaseController;
import com.bean.IsAutoStart;
import com.bean.Lottery;
import com.bean.LotteryLog;
import com.bean.OpenNum;
import com.bean.TaskTimerBean;
import com.bean.TimeLong;
import com.bean.TimeNumOver;

public class TaskTimer extends BaseController{
	private FormString fs = new FormString();
	final Timer timer = new Timer();
	public void startTask(long time) {
		Task uTask = new Task();
		timer.schedule(uTask, time*1000, time*1000);
	}
	
	public boolean stopTimer(){
		System.out.println("停止开奖器");
		TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
		taskt.set("status", 0);
		taskt.set("person", 1);
		taskt.update();
		return true;
	}
	
	private class Task extends TimerTask {
		public Task() {
			System.out.println("开始启动定时器");
			//设置定时器的状态为启动状态
			TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
			taskt.set("status", 1);
			taskt.set("person", 0);
			taskt.update();
			
			//设置重置开奖期数
			/*OpenNum on = OpenNum.dao.findById(1);//查找一天开奖的期数
			on.set("nowNum", 1);
			on.set("spareNum", on.getInt("openNum"));
			on.update();*/
		}

		@Override
		public void run() {
			System.out.println("开奖开始");
			Lottery nowNum = Lottery.dao.findFirst("SELECT * FROM lottery ORDER BY creantime ASC");
			OpenNum on = OpenNum.dao.findById(1);//查找一天开奖的期数
			//if(on.getInt("nowNum")<=on.getInt("openNum")){ //如果已开的期数大于规定的期数则应该是开完了
				LotteryLog llog = new LotteryLog();
				if(null==nowNum){//等于空的时候就说明奖池里面是没有号码了
					IsAutoStart ias = IsAutoStart.dao.findById(1);
					if(ias.getInt("status")==1){ //判断是否设置了自动开奖
						llog.put("qiNum",getYear()+fs.formNum(on.getInt("nowNum")));
						llog.put("Num",fs.getThreeNum());
						llog.put("creantime",getNow());
						List<LotteryLog> ltt = LotteryLog.dao.find("SELECT * FROM lottery_log WHERE qiNum = '"+llog.getStr("qiNum")+"'");
						if(ltt.size()>0){
							for(LotteryLog lol : ltt){
								lol.delete();
							}
						}
						llog.save();
						//重新开始计时
						TimeNumOver tlong = TimeNumOver.dao.findById(1);
						tlong.set("number", (TimeLong.dao.findById(4)).getInt("timelong"));
						tlong.update();
						//重新开始计算期数
						on.set("nowNum", on.getInt("nowNum")+1);
						//on.set("spareNum", on.getInt("spareNum")-1);
						on.update();
					}else{ //否则结束开奖
						System.out.println("开奖结束");
						TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
						taskt.set("status", 0);
						taskt.set("person", 2);
						taskt.update();
						
						TimeNumOver tlong = TimeNumOver.dao.findById(1);
						tlong.set("number", 0);
						tlong.update();
						timer.cancel();
						return;
					}
					
				}else{ //使用奖池里面的号码
					llog.put("qiNum",getYear()+fs.formNum(on.getInt("nowNum")));
					llog.put("Num",nowNum.getInt("Num"));
					llog.put("creantime",getNow());
					List<LotteryLog> ltt = LotteryLog.dao.find("SELECT * FROM lottery_log WHERE qiNum = '"+llog.getStr("qiNum")+"'");
					if(ltt.size()>0){
						for(LotteryLog lol : ltt){
							lol.delete();
						}
					}
					if(llog.save()){
						nowNum.delete();
						on.set("nowNum", on.getInt("nowNum")+1);
						//on.set("spareNum", on.getInt("spareNum")-1);
						on.update();
					}
				}
			/*}else{
				System.out.println("超过今天规定的期数，我要结束掉了");
				TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
				taskt.set("status", 0);
				taskt.set("person", 3);
				taskt.update();
				
				timer.cancel();
				return;
			}*/

			//判断计时器是否结束掉，而且结束的方法必须写在执行run任务结束之后。
			TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
			if(taskt.getInt("status")==0){
				timer.cancel();
			}
		}
		
	}
	
}
