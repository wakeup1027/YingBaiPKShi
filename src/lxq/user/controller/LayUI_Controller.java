package lxq.user.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.IsAutoStart;
import com.bean.Lottery;
import com.bean.LotteryBean;
import com.bean.LotteryLog;
import com.bean.LotteryLogBean;
import com.bean.OpenNum;
import com.bean.TaskTimerBean;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

import demo.UserInterceptor;
import lxq.util.DateUtil;
import lxq.util.FormString;
import lxq.util.QuzarTimer;

@Before(UserInterceptor.class)
@ControllerBind(controllerKey = "/VipCustomer")
public class LayUI_Controller extends BaseController{
	private static final String HomePath = "/admin/LayUI/";
	private static final String HomePathPage = "/admin/LayUI/Page/";
	
	//主页
	public void index(){
		render(HomePath+"home.html");
	}
	
	//下注界面
	public void xiazhuPage(){
		render(HomePathPage+"xiazhu_page.html");
	}
	
	//下注历史记录界面
	public void historyPage(){
		render(HomePathPage+"history_page.html");
	}
	
	//申请提现届满
	public void applyPage(){
		render(HomePathPage+"apply_page.html");
	}
	
	//用户中心
	public void userCenter(){
		render(HomePathPage+"user_center.html");
	}
	
	//充值记录
	public void rechargeLog(){
		render(HomePathPage+"recharge_log.html");
	}
	
	//提现记录
	public void putCashLog(){
		render(HomePathPage+"putcash_log.html");
	}
	
	//未开奖界面跳转
	public void inpNum(){
		render(HomePathPage+"kaijianNum.html");
	}
	
	//加载已开过的数据
	public void loadNoDate(){
		List<Lottery> list = Lottery.dao.find("SELECT * FROM lottery ORDER BY creantime DESC LIMIT "+(getParaToInt("page")-1)*getParaToInt("limit")+","+getParaToInt("limit"));
		List<LotteryBean> newPer = new ArrayList<LotteryBean>();
		for(Lottery pr : list){
			LotteryBean ll = new LotteryBean();
			ll.setId(pr.getInt("id"));
			ll.setNum(pr.getInt("Num"));
			ll.setCreantime(pr.getStr("creantime"));
			newPer.add(ll);
		}
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("count", Db.queryLong("SELECT count(*) FROM lottery"));
		json.put("data", newPer);
		renderJson(json.toJSONString());
	}
	
	//添加未开奖的号码
	public void saveNum(){
		JSONObject json = new JSONObject();
		Lottery ltt = new Lottery();
		ltt.set("Num",Integer.parseInt(getPara("firstNum")+""+getPara("secondNum")+""+getPara("threeNum")));
		ltt.set("creantime",getNow());
		if(ltt.save()){
			json.put("state", "success");
			json.put("msg", "号码录入成功！");
		}else{
			json.put("state", "error");
			json.put("msg", "号码录入失败！请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	//删除未开奖的号码
	public void delNum(){
		JSONObject json = new JSONObject();
		int numid = getParaToInt();
		Lottery ltt = Lottery.dao.findById(numid);
		ltt.delete();
		json.put("state", "success");
		json.put("msg", "号码删除成功！");
		renderJson(json.toJSONString());
	}
	
	//已开奖的号码界面跳转
	public void getoverList(){
		render(HomePathPage+"overNum.html");
	}
	
	//加载已开过的数据
	public void loadOverDate(){
		List<LotteryLog> list = LotteryLog.dao.find("SELECT * FROM lottery_log ORDER BY creantime DESC LIMIT "+(getParaToInt("page")-1)*getParaToInt("limit")+","+getParaToInt("limit"));
		List<LotteryLogBean> newPer = new ArrayList<LotteryLogBean>();
		for(LotteryLog pr : list){
			LotteryLogBean ll = new LotteryLogBean();
			ll.setId(pr.getInt("id"));
			ll.setNum(pr.getInt("Num"));
			ll.setQiNum(pr.getStr("qiNum"));
			ll.setCreantime(pr.getStr("creantime"));
			newPer.add(ll);
		}
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("count", Db.queryLong("SELECT count(*) FROM lottery_log"));
		json.put("data", newPer);
		renderJson(json.toJSONString());
	}
	
	//删除已开过的号码（一个一个删除）
	public void delOldNum(){
		JSONObject json = new JSONObject();
		LotteryLog ltt = new LotteryLog();
		ltt.set("id", getPara("num"));
		if(ltt.delete()){
			json.put("state", "success");
			json.put("msg", "号码删除成功！");
		}else{
			json.put("state", "error");
			json.put("msg", "号码删除失败！请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	//修改开奖号码和创建时间
	public void createUpNum(){
		LotteryLog lo = LotteryLog.dao.findById(getParaToInt("num"));
		lo.set("Num", getPara("OpenNum"));
		lo.set("creantime", getPara("creatime"));
		lo.update();
		JSONObject json = new JSONObject();
		json.put("state", "success");
		renderJson(json.toJSONString());
	}
	
	//录入已开过的号码（一个一个录）
	public void saveOldNum(){
		JSONObject json = new JSONObject();
		LotteryLog ltt = new LotteryLog();
		ltt.set("qiNum",getPara("qiNum"));
		ltt.set("Num",Integer.parseInt(getPara("firstNum")+""+getPara("secondNum")+""+getPara("threeNum")));
		ltt.set("creantime",getPara("creantime"));
		if(ltt.save()){
			json.put("state", "success");
			json.put("msg", "号码录入成功！");
		}else{
			json.put("state", "error");
			json.put("msg", "号码录入失败！请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	//一键录入号码
	public void iptAutoNum(){
		DateUtil DU = new DateUtil();
		FormString fs = new FormString();
		String dateStr = getPara("dateStr");
		int timeNum = getParaToInt("timeNum");
		int forNum = getParaToInt("forNum");
		for(int i=1; i<=forNum; i++){
			OpenNum openn = OpenNum.dao.findById(1);
			String creantime = DU.getTime(dateStr, timeNum);
			LotteryLog lott = new LotteryLog();
			lott.set("creantime", creantime);
			lott.set("qiNum",creantime.substring(0, 4)+new FormString().formNum(openn.getInt("nowNum")));
			lott.set("Num", fs.getThreeNum());
			lott.save();
			openn.set("nowNum", openn.getInt("nowNum")+1);
			openn.update();
			dateStr = creantime;
		}
		JSONObject json = new JSONObject();
		json.put("state", "success");
		renderJson(json.toJSONString());
	}
	
	//清空开奖记录
	public void cleatLogNum(){
		Db.update("TRUNCATE TABLE lottery_log");
		OpenNum nup = OpenNum.dao.findById(1);
		nup.set("nowNum", 1);
		nup.update();
			
		JSONObject json = new JSONObject();
		json.put("state", "success");
		renderJson(json.toJSONString());
	}
	
	//获取开奖器状态
	public void getTaskStautus(){
		TaskTimerBean tkb = TaskTimerBean.dao.findById(1);
		setAttr("tkb",tkb);
		render(HomePathPage+"TaskTime.html");
	}
	
	//启动开奖器
	public void start(){
		/*Tiemer.timer1();
		TiemerSecond.timer1();//这个定时器是同步前后端的倒计时定时器
		JSONObject json = new JSONObject();
		json.put("state", true);
		renderJson(json.toJSONString());*/
		QuzarTimer qazt = QuzarTimer.getInstance();
		qazt.starQuzar();
		
		JSONObject json = new JSONObject();
		json.put("state", true);
		renderJson(json.toJSONString());
	}
	
	//停止开奖器
	public void stop(){
		QuzarTimer qazt = QuzarTimer.getInstance();
		qazt.stopQuzar();
		
		JSONObject json = new JSONObject();
		json.put("state", true);
		renderJson(json.toJSONString());
	}
	
	//获取自动开奖状态
	public void setAutoStautus(){
		IsAutoStart tkb = IsAutoStart.dao.findById(1);
		setAttr("tkb",tkb);
		render(HomePathPage+"isAutoStart.html");
	}
	
	//开启自动开奖设置
	public void starAuto(){
		IsAutoStart ysd = IsAutoStart.dao.findById(1);
		ysd.set("status", 1);
		JSONObject json = new JSONObject();
		if(ysd.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	//关闭自动开奖的设置
	public void stopAuto(){
		IsAutoStart ysd = IsAutoStart.dao.findById(1);
		ysd.set("status", 0);
		JSONObject json = new JSONObject();
		if(ysd.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
}
