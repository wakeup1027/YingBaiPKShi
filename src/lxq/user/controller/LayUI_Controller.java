package lxq.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.BetsDataLog_Bean;
import com.bean.ApplyMoneyLog_Bean;
import com.bean.BetsData;
import com.bean.ApplyMoney;
import com.bean.ApplyMoneyLog;
import com.bean.BetsDataLog;
import com.bean.Recharge;
import com.bean.RechargeNow;
import com.bean.Recharge_Bean;
import com.bean.UserInfo;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

import demo.UserInterceptor;

@Before(UserInterceptor.class)
@ControllerBind(controllerKey = "/VipCustomer")
public class LayUI_Controller extends BaseController{
	private static final String HomePath = "/admin/LayUI/";
	private static final String HomePathPage = "/admin/LayUI/Page/";
	
	//主页
	public void index(){
		String userid = getSessionAttr("UserId");
		UserInfo uinfo = UserInfo.dao.findById(userid);
		setAttr("userm",uinfo.get("fd_username"));
		setAttr("money",uinfo.get("fd_money"));
		setAttr("icemoney",uinfo.get("fd_icemoney"));
		render(HomePath+"home.html");
	}
	
	//会员充值
	public void recharge(){
		String userid = getSessionAttr("UserId");
		RechargeNow rechar = RechargeNow.dao.findFirst("SELECT * FROM rechargenow WHERE fd_userid = '"+userid+"'");
		if(null==rechar){
			RechargeNow rch = new RechargeNow();
			rch.set("fd_status", "-1");
			setAttr("rechar",rch);
		}else{
			setAttr("rechar",rechar);
		}
		render(HomePathPage+"recharge.html");
	}
	
	//会员充值提交的信息
	public void subRechar(){
		JSONObject json = new JSONObject();
		String zhifuType = getPara("reTy");  
		String orderNum = getPara("ormn");
		Recharge fdf = Recharge.dao.findFirst("SELECT * FROM recharge WHERE fd_ordernum = '"+orderNum+"'");
		if(fdf!=null){
			json.put("state", "error");
			json.put("mes", "订单号已被使用！");
			renderJson(json.toJSONString());
			return;
		}
		int money = getParaToInt("nom");
		String userid = getSessionAttr("UserId");
		String username = getSessionAttr("loginUser");
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		RechargeNow recge = new RechargeNow();
		recge.set("fd_id", uuid);
		recge.set("fd_money", money);
		recge.set("fd_userid", userid);
		recge.set("fd_username", username);
		recge.set("fd_status", "0");
		recge.set("fd_creatime", sdf.format(now));
		recge.set("fd_ordernum", orderNum);
		recge.set("fd_ordertype", zhifuType);
		boolean sares = recge.save();
		if(sares){
			json.put("state", "success");
			json.put("mes", "信息提交成功！");
		}else{
			json.put("state", "error");
			json.put("mes", "系统繁忙，请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	//下注界面
	public void xiazhuPage(){
		render(HomePathPage+"xiazhu_page.html");
	}
	
	//下注历史记录界面
	public void historyPage(){
		render(HomePathPage+"history_page.html");
	}
	
	//下注功能
	public void newBetsdata(){
		JSONObject json = new JSONObject();
		int sum = 0;
		String jsodata = getPara("strj");
		String userid = getSessionAttr("UserId");
		UserInfo uinfo = UserInfo.dao.findById(userid);
		List<BetsData> betli = new ArrayList<BetsData>();
		JSONArray jsonArray = JSON.parseArray(jsodata);
		for (int i = 0; i < jsonArray.size(); i++){
            BetsData btd = new BetsData();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String type = jsonObject.getString("typeNum");
            String num = jsonObject.getString("zhus");
            int dzMoney = jsonObject.getInteger("monshu");
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
            btd.set("fd_id", uuid);
            btd.set("fd_userid", userid);
            btd.set("fd_username", uinfo.get("fd_username"));
            btd.set("fd_type", type);
            btd.set("fd_num", num);
            btd.set("fd_qishu", "688747");
            btd.set("fd_creatime", sdf.format(now));
            btd.set("fd_tatol", dzMoney);
            betli.add(btd);
            sum += dzMoney;
        }
		if(sum>uinfo.getInt("fd_money")){
			json.put("state", "error");
			json.put("mes", "余额不足！");
		}else{
			boolean bl = false;
			for(BetsData bt : betli){
				try {
					bt.save();
				} catch (Exception e) {
					bl = true;
					System.out.println(e);
				}
			}
			if(bl){
				json.put("state", "error");
				json.put("mes", "保存出错！");
			}else{
				json.put("state", "success");
				json.put("mes", "下注成功！");
			}
		}
		renderJson(json.toJSONString());
	}
	
	//申请提现界面
	public void applyPage(){
		String userid = getSessionAttr("UserId");
		ApplyMoney amoney = ApplyMoney.dao.findFirst("SELECT * FROM applymoney WHERE fd_userid = '"+userid+"' AND (fd_status = '0' OR fd_status = '1')");
		if(null!=amoney){
			setAttr("user",amoney.get("fd_username"));
			setAttr("money",amoney.get("fd_money"));
			setAttr("status",amoney.get("fd_status"));
		}else{
			setAttr("status","2");
		}
		render(HomePathPage+"apply_page.html");
	}
	
	//用户中心
	public void userCenter(){
		String userid = getSessionAttr("UserId");
		UserInfo uinfo = UserInfo.dao.findById(userid);
		if(uinfo!=null){
			setAttr("name",uinfo.get("fd_truename"));
			setAttr("username",uinfo.get("fd_username"));
			setAttr("idcase",uinfo.get("fd_IDcase"));
			setAttr("banka",uinfo.get("fd_bank"));
			setAttr("phonenum",uinfo.get("fd_phone"));
			render(HomePathPage+"user_center.html");
		}else{
			render("/");//返回登录页重新登录
		}
	}
	
	//用户修改自己的信息（身份证号码） 
	public void upidcase(){
		JSONObject json = new JSONObject();
		String userid = getSessionAttr("UserId");
		UserInfo uifo = UserInfo.dao.findById(userid);
		uifo.set("fd_IDcase", getPara("newInfodate"));
		boolean doYes = uifo.update();
		if(doYes){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	//用户修改自己的信息（手机号码）
	public void upphonenum(){
		JSONObject json = new JSONObject();
		String userid = getSessionAttr("UserId");
		UserInfo uifo = UserInfo.dao.findById(userid);
		uifo.set("fd_phone", getPara("newInfodate"));
		boolean doYes = uifo.update();
		if(doYes){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	//用户修改自己的信息（银行号码）
	public void upbanka(){
		JSONObject json = new JSONObject();
		String userid = getSessionAttr("UserId");
		UserInfo uifo = UserInfo.dao.findById(userid);
		uifo.set("fd_bank", getPara("newInfodate"));
		boolean doYes = uifo.update();
		if(doYes){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
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
	
	//申请提现功能
	public void ApplyVoid(){
		JSONObject json = new JSONObject();
		String userid = getSessionAttr("UserId");
		String agenPass = getPara("fdpassword");
		String truePass = getSessionAttr("Password");
		//比较密码是否正确
		if(agenPass.equals(truePass)){
			UserInfo uifo = UserInfo.dao.findById(userid);
			int yuem = uifo.getInt("fd_money");//账户余额
			int getm = getParaToInt("cash");//申请提现金额
			//账户余额必须大于或等于提现余额
			if(yuem>=getm){
				int iceingm = uifo.getInt("fd_icemoney");//账户正在冻结的余额
				if(iceingm==0){
					//用账户余额里面的现金减去提现金额当成是冻结金额
					uifo.set("fd_money", yuem-getm);
					uifo.set("fd_icemoney",getm);
					uifo.update();
					//保存提现申请
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					ApplyMoney model = new ApplyMoney();
					String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
					model.set("fd_id", uuid);
					model.set("fd_money", getm);
					model.set("fd_userid", userid);
					model.set("fd_username", getSessionAttr("loginUser"));
					model.set("fd_status", "0");
					model.set("fd_creatime",sdf.format(now));
					model.set("fd_commit", getPara("commit"));
					model.save();
					json.put("mes", "请求已受理，请耐心等待客服审核...");
					json.put("code", 200);
				}else{
					json.put("mes", "账户存在冻结的余额，不能申请提现！请耐心等待客服处理之后再申请...");
					json.put("code", 502);
				}
			}else{
				json.put("mes", "账户余额不足！");
				json.put("code", 501);
			}
			
		}else{
			json.put("mes", "密码不正确！");
			json.put("code", 500);
		}
		renderJson(json.toJSONString());
	}
	
	//充值记录数据
	public void RechargeLog(){
		String userid = getPara("fd_userid");
		List<Recharge> list = Recharge.dao.find("SELECT * FROM recharge WHERE fd_userid = '"+userid+"' ORDER BY fd_creatime DESC LIMIT "+(getParaToInt("page")-1)*getParaToInt("limit")+","+getParaToInt("limit"));
		List<Recharge_Bean> newPer = new ArrayList<Recharge_Bean>();
		for(Recharge pr : list){
			Recharge_Bean ll = new Recharge_Bean();
			ll.setFd_id(pr.getStr("fd_id"));
			ll.setFd_money(pr.getInt("fd_money"));
			ll.setFd_userid(pr.getStr("fd_userid"));
			ll.setFd_username(pr.getStr("fd_username"));
			ll.setFd_status(pr.getStr("fd_status"));
			ll.setFd_creatime(pr.getDate("fd_creatime")+"");
			ll.setFd_arraytime(pr.getDate("fd_arraytime")+"");
			ll.setFd_ordernum(pr.getStr("fd_ordernum"));
			ll.setFd_ordertype(pr.getStr("fd_ordertype"));
			newPer.add(ll);
		}
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("count", Db.queryLong("SELECT count(*) FROM recharge WHERE fd_userid = '"+userid+"'"));
		json.put("data", newPer);
		renderJson(json.toJSONString());
	}
	
	//下注记录
	public void BetsDataLog(){
		String userid = getPara("fd_userid");
		List<BetsDataLog> list = BetsDataLog.dao.find("SELECT * FROM betsdatalog WHERE fd_userid = '"+userid+"' ORDER BY fd_creatime DESC LIMIT "+(getParaToInt("page")-1)*getParaToInt("limit")+","+getParaToInt("limit"));
		List<BetsDataLog_Bean> newPer = new ArrayList<BetsDataLog_Bean>();
		for(BetsDataLog pr : list){
			BetsDataLog_Bean ll = new BetsDataLog_Bean();
			ll.setFd_id(pr.getStr("fd_id"));
			ll.setFd_userid(pr.getStr("fd_userid"));
			ll.setFd_username(pr.getStr("fd_username"));
			ll.setFd_type(pr.getStr("fd_type"));
			ll.setFd_num(pr.getStr("fd_num"));
			ll.setFd_zhushu(pr.getStr("fd_zhushu"));
			ll.setFd_qishu(pr.getStr("fd_qishu"));
			ll.setFd_tatol(pr.getInt("fd_tatol"));
			ll.setFd_iswin(pr.getStr("fd_iswin"));
			ll.setFd_creatime(pr.getDate("fd_creatime")+"");
			newPer.add(ll);
		}
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("count", Db.queryLong("SELECT count(*) FROM betsdatalog WHERE fd_userid = '"+userid+"'"));
		json.put("data", newPer);
		renderJson(json.toJSONString());
	}
	
	//提现记录
	public void ApplyMoneyLog(){
		String userid = getPara("fd_userid");
		List<ApplyMoneyLog> list = ApplyMoneyLog.dao.find("SELECT * FROM applymoneylog WHERE fd_userid = '"+userid+"' ORDER BY fd_creatime DESC LIMIT "+(getParaToInt("page")-1)*getParaToInt("limit")+","+getParaToInt("limit"));
		List<ApplyMoneyLog_Bean> newPer = new ArrayList<ApplyMoneyLog_Bean>();
		for(ApplyMoneyLog pr : list){
			ApplyMoneyLog_Bean ll = new ApplyMoneyLog_Bean();
			ll.setFd_id(pr.getStr("fd_id"));
			ll.setFd_money(pr.getInt("fd_money"));
			ll.setFd_userid(pr.getStr("fd_userid"));
			ll.setFd_username(pr.getStr("fd_username"));
			ll.setFd_status(pr.getStr("fd_status"));
			ll.setFd_creatime(pr.getDate("fd_creatime")+"");
			newPer.add(ll);
		}
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("count", Db.queryLong("SELECT count(*) FROM applymoneylog WHERE fd_userid = '"+userid+"'"));
		json.put("data", newPer);
		renderJson(json.toJSONString());
	}
	
	//加载已开过的数据
	public void loadNoDate(){
		/*DateUtil DU = new DateUtil();
		String creantime = DU.getTime(dateStr, timeNum);*/
	}
	
	
}
