package lxq.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.ApplyMoney;
import com.bean.BetsDataLog;
import com.bean.OpenNumber;
import com.bean.Recharge;
import com.bean.UserInfo;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import demo.AdminInterceptor;

@Before(AdminInterceptor.class)
@ControllerBind(controllerKey = "/AdminStrUrl")
public class EasyUI_Controller extends BaseController{
	
	//首页
	public void index(){
		//查看开到多少期了
		OpenNumber onum = OpenNumber.dao.findFirst("SELECT * FROM opennumber ORDER BY fd_qishu DESC");
		List<BetsDataLog> btlist = BetsDataLog.dao.find("SELECT * FROM betsdatalog WHERE fd_qishu = '"+(onum.getInt("fd_qishu")+1)+"'");
		setAttr("btlist",btlist);
		setAttr("opennum",onum.getInt("fd_qishu")+1);
		setAttr("montotal",BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_qishu = '"+(onum.getInt("fd_qishu")+1)+"'"));
		render("/admin/EasyUI/index.html");
	}
	
	//充值管理
	public void rechargePage(){
		render("/admin/EasyUI/recharge.html");
	}
	
	//提现管理
	public void pucashPage(){
		render("/admin/EasyUI/pucash_page.html");
	}
	
	//用户中奖查看
	public void userwinPage(){
		render("/admin/EasyUI/userwin_page.html");
	}
	
	//输赢查看
	public void historyPage(){
		render("/admin/EasyUI/history_page.html");
	}
	
	//用户管理
	public void userInfoPage(){
		render("/admin/EasyUI/userInfo_page.html");
	}
	
	//系统设置
	public void systemSet(){
		render("/admin/EasyUI/system_set.html");
	}
	
	//充值管理加载数据
	public void loadrecherge(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<Recharge> UI = Recharge.dao.findByPage(page, rows, "");
		Long total = Recharge.dao.count("SELECT * FROM recharge");
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
	}
	
	//修改充值状态
	public void cliksrecha(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			Recharge uif = Recharge.dao.findById(sd);
			String usid = uif.getStr("fd_userid");
			UserInfo useif = UserInfo.dao.findById(usid);
			useif.set("fd_money", useif.getInt("fd_money")+uif.getInt("fd_money"));
			uif.set("fd_arraytime", sdf.format(now));
			uif.set("fd_status", "1");
			try {
				if(useif.update()){
					uif.update();
				}
			} catch (Exception e) {
				doUp = true;
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//加载提现记录数据
	public void loadpucashPage(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<ApplyMoney> UI = ApplyMoney.dao.findByPage(page, rows, "ORDER BY fd_status ASC");
		Long total = ApplyMoney.dao.count("SELECT * FROM applymoney");
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
	}
	
	//修改提现状态
	public void upstatus(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String upStutas = getPara("upStutas");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			ApplyMoney uif = ApplyMoney.dao.findById(sd);
			String usid = uif.getStr("fd_userid");
			UserInfo useif = UserInfo.dao.findById(usid);
			useif.set("fd_applymoney", 0);
			uif.set("fd_arraytime", sdf.format(now));
			uif.set("fd_status", upStutas);
			try {
				if(useif.update()){
					uif.update();
				}
			} catch (Exception e) {
				doUp = true;
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//加载用户下注信息
	public void loadwindate(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<BetsDataLog> UI = BetsDataLog.dao.findByPage(page, rows, "ORDER BY fd_qishu DESC");
		Long total = BetsDataLog.dao.count("SELECT * FROM betsdatalog");
		map.put("rows", UI);
	    map.put("total", total); 
	    //获取用户赢的记录
	    map.put("win", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_iswin = '1'"));
	    //输
	    map.put("fualt", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_iswin = '0'"));
	    //未开奖
	    map.put("onopen", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_iswin = '2'"));
	    renderJson(map);
	}
	
	//用户界面操作动作
	public void loaduserd(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<UserInfo> UI = UserInfo.dao.findByPage(page, rows, "");
		Long total = UserInfo.dao.count("SELECT * FROM userinfo");
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
		
	}
	
	//激活用户
	public void clikUser(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			UserInfo uif = UserInfo.dao.findById(sd);
			uif.set("fd_status", "1");
			try {
				uif.update();
			} catch (Exception e) {
				doUp = true;
				System.out.println("激活用户出现失败！");
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//激活用户
	public void djieUser(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			UserInfo uif = UserInfo.dao.findById(sd);
			uif.set("fd_status", "2");
			try {
				uif.update();
			} catch (Exception e) {
				doUp = true;
				System.out.println("冻结用户出现失败！");
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//废弃用户
	public void detetUser(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			UserInfo uif = new UserInfo();
			uif.set("id", sd);
			try {
				uif.delete();
			} catch (Exception e) {
				doUp = true;
				System.out.println("废弃用户出现失败！");
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//开始定时器
	/*public void startTest(){
		JSONObject json = new JSONObject();
		QuzarTimer.getInstance().Quzar();
		json.put("status", 1);
		renderJson(json.toJSONString());
	}*/
}