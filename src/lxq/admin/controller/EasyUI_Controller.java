package lxq.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.Answear;
import com.bean.Answear_Bean;
import com.bean.ApplyMoney;
import com.bean.BetsDataLog;
import com.bean.KefuMes;
import com.bean.Message;
import com.bean.OpenNumber;
import com.bean.Recharge;
import com.bean.SecondTable;
import com.bean.SystemMess;
import com.bean.UserInfo;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;

import demo.AdminInterceptor;
import lxq.util.MD5Util;

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
	
	//公告管理
	public void historyPage(){
		render("/admin/EasyUI/history_page.html");
	}
	
	//用户管理
	public void userInfoPage(){
		render("/admin/EasyUI/userInfo_page.html");
	}
	
	//开奖号码界面
	public void openNums(){
		render("/admin/EasyUI/kaijiangqi.html");
	}
	
	//客服信息管理
	public void kefuMess(){
		render("/admin/EasyUI/kefuxinxi.html");
	}
	
	//系统设置
	public void systemSet(){
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		setAttr("st",st);
		render("/admin/EasyUI/system_set.html");
	}
	
	//加载开奖号码
	public void loadOpenNum(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<OpenNumber> UI = OpenNumber.dao.findByPage(page, rows, "ORDER BY fd_qishu DESC");
		Long total = OpenNumber.dao.count("SELECT * FROM opennumber");
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
	}
	
	//补全号码
	public void savestatus(){
		JSONObject json = new JSONObject();
		int openqishu = getParaToInt("onu");
		String numbe = getPara("upStutas");
		String openTime = getPara("failreason");
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		OpenNumber opb = new OpenNumber();
		opb.set("fd_id", uuid);
		opb.set("fd_qishu", openqishu);
		opb.set("fd_number", numbe);
		opb.set("fd_creatime", openTime);
		if(opb.save()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//删除开奖的号码
	public void deldate(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			try {
				Db.update("DELETE FROM opennumber WHERE fd_id = '"+sd+"'");
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
	
	//查找开奖结果
	public void findnum(){
		JSONObject json = new JSONObject();
		int qisjh = getParaToInt("onu");
		OpenNumber opb = OpenNumber.dao.findFirst("SELECT * FROM opennumber WHERE fd_qishu = "+qisjh);
		if(opb!=null){
			json.put("openqishu", opb.getInt("fd_qishu"));
			json.put("opennum", opb.getStr("fd_number"));
			json.put("opentime", opb.getDate("fd_creatime"));
		}else{
			json.put("openqishu", "未找到结果");
			json.put("opennum", "未找到结果");
			json.put("opentime", "未找到结果");
		}
		renderJson(json.toJSONString());
	}
	
	//修改赔率
	public void uplost(){
		JSONObject json = new JSONObject();
		double s = Double.parseDouble(getPara("nom"));
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("lostnum", s);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//修改倒计时
	public void updlk(){
		JSONObject json = new JSONObject();
		double s = getParaToInt("nom");
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("closetime", s);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//手动封盘
	public void upstusfp(){
		JSONObject json = new JSONObject();
		String s = getPara("nom");
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("fenpan", s);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//接收微信二维码
	public void uperjlimg(){
		JSONObject json = new JSONObject();
		UploadFile uf = getFile("Filedata", "");
		String filename = uf.getFileName();
		int typeNum = filename.lastIndexOf(".");
		String type = filename.substring(typeNum, filename.length());
		String mHttpUrl = getSession().getServletContext().getRealPath("/")+"upload/";
		File f = uf.getFile();
		String newfilename = System.currentTimeMillis()+type;
		f.renameTo(new File(mHttpUrl+newfilename));
		//记录图片路径写库
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("wachat", "upload/"+newfilename);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//接收支付宝二维码
	public void uperjlzhifimg(){
		JSONObject json = new JSONObject();
		UploadFile uf = getFile("Filedata", "");
		String filename = uf.getFileName();
		int typeNum = filename.lastIndexOf(".");
		String type = filename.substring(typeNum, filename.length());
		String mHttpUrl = getSession().getServletContext().getRealPath("/")+"upload/";
		File f = uf.getFile();
		String newfilename = System.currentTimeMillis()+type;
		f.renameTo(new File(mHttpUrl+newfilename));
		//记录图片路径写库
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("zhifb", "upload/"+newfilename);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//公告管理加载数据
	public void loadtongzhi(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<SystemMess> UI = SystemMess.dao.findByPage(page, rows, "ORDER BY fd_createtime DESC");
		Long total = Recharge.dao.count("SELECT * FROM systemmess");
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//新增公告管理加载数据
	public void adtongzhi(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		SystemMess smes = new SystemMess();
		smes.set("id", uuid);
		smes.set("fd_connect", orderStr);
		smes.set("fd_createtime", sdf.format(now));
		if(smes.save()){
			json.put("status", 1);
		}else{
			json.put("status", 0);
		}
		renderJson(json.toJSONString());
	}
	
	//修改公告管理加载数据
	public void uptongzhi(){
		JSONObject json = new JSONObject();
		String uinum = getPara("uinum");
		String orderStr = getPara("onu");
		SystemMess smes = SystemMess.dao.findById(uinum);
		smes.set("fd_connect", orderStr);
		if(smes.update()){
			json.put("status", 1);
		}else{
			json.put("status", 0);
		}
		renderJson(json.toJSONString());
	}
	
	//删除公告管理
	public void detettongzhi(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SystemMess uif = new SystemMess();
			uif.set("id", sd);
			try {
				uif.delete();
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
		
	//充值管理加载数据
	public void loadrecherge(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<Recharge> UI = Recharge.dao.findByPage(page, rows, "ORDER BY fd_creatime DESC");
		Long total = Recharge.dao.count("SELECT * FROM recharge");
		map.put("taoslm", Recharge.dao.findFirst("SELECT SUM(fd_money) AS total FROM recharge"));
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
	}
	
	//客服管理加载数据
	public void loadkefuMes(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<KefuMes> UI = KefuMes.dao.findByPage(page, rows, "");
		Long total = KefuMes.dao.count("SELECT * FROM kefuMes");
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//加载问题的回答
	public void loadAnswear(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		KefuMes km = KefuMes.dao.findById(orderStr);
		km.set("fd_kfread", "1");
		km.update();
		List<Answear> aw = Answear.dao.find("SELECT * FROM answear WHERE fd_parent_id = '"+orderStr+"' ORDER BY fd_createtime ASC");
		List<Answear_Bean> ab = new ArrayList<Answear_Bean>();
		for(Answear as : aw){
			Answear_Bean abs = new Answear_Bean();
			abs.setConnect(as.getStr("fd_connect"));
			abs.setCreatetime(as.getDate("fd_createtime")+"");
			abs.setType(as.getStr("fd_type"));
			ab.add(abs);
		}
		json.put("datas", ab);
		renderJson(json.toJSONString());
	}
	
	//删除客服信息
	public void deletkefmes(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			try {
				Db.update("DELETE FROM answear WHERE fd_parent_id = '"+sd+"'");
				KefuMes.dao.deleteById(sd);
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
	
	//用户提问提交的方法
	public void addquenfunc(){
		String userid = getPara("onu");
		String username = (UserInfo.dao.findById(userid)).getStr("fd_username");
		String sd = getPara("sd");
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		KefuMes kfm = new KefuMes();
		kfm.set("id", uuid);
		kfm.set("fd_connect", "【来自客服】"+sd);
		kfm.set("fd_createtime", sdf.format(now));
		kfm.set("fd_creater", userid);
		kfm.set("fd_name", username);
		kfm.set("fd_kfread", "1");
		kfm.set("fd_useread", "0");
		JSONObject json = new JSONObject();
		if(kfm.save()){
			json.put("status", "1");
		}else{
			json.put("status", "0");
		}
		renderJson(json);
	}
	
	//联系客服查找加载数据
	public void findcallkef(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWord = getPara("keyowl");
		String upStutas = getPara("upStutas");
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		String wherestr = "WHERE 1=1";
		if(!keyWord.equals("")){
			wherestr+=" AND fd_connect LIKE '%"+keyWord+"%'";
		}
		if(!upStutas.equals("")){
			wherestr+=" AND fd_kfread='"+upStutas+"'";
		}
		List<KefuMes> UI = KefuMes.dao.findByPage(wherestr, page, rows,"fd_createtime");
		Long total = KefuMes.dao.count("SELECT * FROM kefumes "+wherestr);
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//用户继续提问
	public void aginanswear(){
		String fdid = getPara("fdid");
		String valupe = getPara("valupe");
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		Answear aw = new Answear();
		aw.set("id", uuid);
		aw.set("fd_parent_id", fdid);
		aw.set("fd_connect", valupe);
		aw.set("fd_createtime", sdf.format(now));
		aw.set("fd_creater", "admin");
		aw.set("fd_username", "admin");
		aw.set("fd_type", "fuke");
		JSONObject json = new JSONObject();
		if(aw.save()){
			KefuMes kf = KefuMes.dao.findById(fdid);
			kf.set("fd_useread", "0");
			kf.update();
			json.put("status", "1");
		}else{
			json.put("status", "0");
		}
		renderJson(json);
	}
	
	//充值查找加载数据
	public void findrecherge(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWord = getPara("keyowl");
		String upStutas = getPara("upStutas");
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		String wherestr = "WHERE 1=1";
		if(!keyWord.equals("")){
			wherestr+=" AND fd_username='"+keyWord+"'";
		}
		if(!upStutas.equals("")){
			wherestr+=" AND fd_status='"+upStutas+"'";
		}
		List<Recharge> UI = Recharge.dao.findByPage(wherestr, page, rows,"fd_creatime");
		Long total = Recharge.dao.count("SELECT * FROM recharge "+wherestr);
		map.put("taoslm", Recharge.dao.findFirst("SELECT SUM(fd_money) AS total FROM recharge "+wherestr));
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//修改充值状态成功
	public void cliksrecha(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			Recharge uif = Recharge.dao.findById(sd);
			if(uif.getStr("fd_status").equals("0")){//只有审核中的数据才能修改
				String usid = uif.getStr("fd_userid");
				UserInfo useif = UserInfo.dao.findById(usid);
				useif.set("fd_money", useif.getDouble("fd_money")+uif.getDouble("fd_money"));
				uif.set("fd_arraytime", sdf.format(now));
				uif.set("fd_status", "1");
				try {
					if(useif.update()){
						//更新充值数据状态
						uif.update();
						//新增系统消息
						Message ms = new Message();
						String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
						ms.set("id", uuid);
						ms.set("fd_creatime", sdf.format(now));
						ms.set("fd_type", "0");
						ms.set("fd_ready", "0");
						ms.set("fd_senduser", useif.getStr("id"));
						ms.set("fd_title", "恭喜您充值状态的数据已经充值成功，充值金额："+uif.getDouble("fd_money"));
						ms.set("fd_connet", "【系统消息】恭喜您充值状态的数据已经充值成功，充值金额："+uif.getDouble("fd_money"));
						ms.save();
					}
				} catch (Exception e) {
					doUp = true;
				}
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//修改充值状态失败
	public void upsrecha(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			Recharge uif = Recharge.dao.findById(sd);
			if(uif.getStr("fd_status").equals("0")){//只有审核中的数据才能修改
				uif.set("fd_arraytime", sdf.format(now));
				uif.set("fd_status", "2");
				uif.set("fd_commit", "系统暂未收到付款，请认真核对支付单号再重新发起一遍，如果有误，请联系客服通过其他方式确认！");
				try {
					uif.update();
					//新增系统消息
					Message ms = new Message();
					String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
					ms.set("id", uuid);
					ms.set("fd_creatime", sdf.format(now));
					ms.set("fd_type", "0");
					ms.set("fd_ready", "0");
					ms.set("fd_senduser", uif.getStr("fd_userid"));
					ms.set("fd_title", "很遗憾，您申请充值的金额："+uif.getDouble("fd_money")+"申请失败");
					ms.set("fd_connet", "很遗憾，您申请充值的金额："+uif.getDouble("fd_money")+"申请失败，可能充值未到账或者订单号出错，如果没问题可以通过联系客服让客服帮忙处理。或者重新提交充值申请，客服会热情处理保障您的利益");
					ms.save();
				} catch (Exception e) {
					doUp = true;
				}
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
		List<ApplyMoney> UI = ApplyMoney.dao.findByPage(page, rows, "ORDER BY fd_creatime DESC");
		Long total = ApplyMoney.dao.count("SELECT * FROM applymoney");
		map.put("taoslm", ApplyMoney.dao.findFirst("SELECT SUM(fd_money) AS total FROM applymoney"));
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
	}
	
	//加载查找提现记录数据
	public void findpucashPage(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWord = getPara("keyowl");
		String upStutas = getPara("upStutas");
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		String wherestr = "WHERE 1=1";
		if(!keyWord.equals("")){
			wherestr+=" AND fd_username='"+keyWord+"'";
		}
		if(!upStutas.equals("")){
			wherestr+=" AND fd_status='"+upStutas+"'";
		}
		List<ApplyMoney> UI = ApplyMoney.dao.findByPage(wherestr, page, rows,"fd_status");
		Long total = ApplyMoney.dao.count("SELECT * FROM applymoney "+wherestr);
		//获取查找用户的提现总金额
	    map.put("taoslm", ApplyMoney.dao.findFirst("SELECT SUM(fd_money) AS total FROM applymoney "+wherestr));
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//修改提现状态
	public void upstatus(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String upStutas = getPara("upStutas");
		String failreason = getPara("failreason");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			ApplyMoney uif = ApplyMoney.dao.findById(sd);
			if(uif.getStr("fd_status").equals("0")||uif.getStr("fd_status").equals("1")){
				String usid = uif.getStr("fd_userid");
				UserInfo useif = UserInfo.dao.findById(usid);
				uif.set("fd_arraytime", sdf.format(now));
				uif.set("fd_status", upStutas);
				uif.set("fd_failreason", failreason);
				try {
					boolean yesavs = uif.update();
					Message ms = new Message();
					if(yesavs&&upStutas.equals("2")){//提现完成才要把用户信息中的提现金额清空
						ms.set("fd_title", "您申请提现的申请客服申请提现成功，提现金额："+useif.getDouble("fd_applymoney"));
						ms.set("fd_connet", "【系统消息】您申请提现的申请客服申请提现成功，提现金额："+useif.getDouble("fd_applymoney"));
						useif.set("fd_applymoney", 0);
						useif.update();
					}else if(yesavs&&upStutas.equals("3")){//如果提现失败，则退回提现金额到账户余额里面
						ms.set("fd_title", "您申请提现的申请客服申请提现失败，提现金额："+useif.getDouble("fd_applymoney"));
						ms.set("fd_connet", "【系统消息】您申请提现的申请客服申请提现失败，提现金额："+useif.getDouble("fd_applymoney")+"，失败原因："+failreason);
						useif.set("fd_money", useif.getDouble("fd_money")+useif.getDouble("fd_applymoney"));
						useif.set("fd_applymoney", 0);
						useif.update();
					}else if(yesavs&&upStutas.equals("1")){
						ms.set("fd_title", "您申请提现的申请客服已受理，提现金额："+useif.getDouble("fd_applymoney"));
						ms.set("fd_connet", "【系统消息】您申请提现的申请客服已受理，提现金额："+useif.getDouble("fd_applymoney")+"，客服确认满足提现要求之后会提现成功！");
					}
					String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
					ms.set("id", uuid);
					ms.set("fd_creatime", sdf.format(now));
					ms.set("fd_type", "0");
					ms.set("fd_ready", "0");
					ms.set("fd_senduser", uif.getStr("fd_userid"));
					ms.save();
				} catch (Exception e) {
					doUp = true;
				}
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
	
	//加载用户下注信息
	public void findwindate(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWordNum = getPara("keyowlnum");
		String keyWordUsm = getPara("keyowlusm");
		String strtm = getPara("strtm");
		String overm = getPara("overm");
		String upStutast = getPara("upStutast");
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		String wherestr = "WHERE 1=1";
		if(!keyWordNum.equals("")){
			wherestr+=" AND fd_qishu='"+keyWordNum+"'";
		}
		if(!keyWordUsm.equals("")){
			wherestr+=" AND fd_username='"+keyWordUsm+"'";
		}
		if(!strtm.equals("")){
			wherestr+=" AND fd_creatime>='"+strtm+"'";
		}
		if(!overm.equals("")){
			wherestr+=" AND fd_creatime<='"+overm+"'";
		}
		if(!upStutast.equals("")){
			wherestr+=" AND fd_iswin='"+upStutast+"'";
		}
		List<BetsDataLog> UI = BetsDataLog.dao.findByPage(wherestr, page, rows,"fd_iswin");
		Long total = BetsDataLog.dao.count("SELECT * FROM betsdatalog "+wherestr);
		map.put("rows", UI);
		map.put("total", total); 
		//获取用户赢的记录
		map.put("win", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog "+wherestr+" AND fd_iswin = '1'"));
		//输
		map.put("fualt", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog "+wherestr+" AND fd_iswin = '0'"));
		//未开奖
		map.put("onopen", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog "+wherestr+" AND fd_iswin = '2'"));
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
	
	//用户界面操作动作
	public void finduserd(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWordUsm = getPara("keyowl");
		String upStutas = getPara("upStutas");
		String wherestr = "WHERE 1=1";
		if(!keyWordUsm.equals("")){
			wherestr+=" AND fd_username='"+keyWordUsm+"'";
		}
		if(!upStutas.equals("")){
			wherestr+=" AND fd_status='"+upStutas+"'";
		}
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<UserInfo> UI = UserInfo.dao.findByPage(wherestr, page, rows, "fd_creatime");
		Long total = UserInfo.dao.count("SELECT * FROM userinfo "+wherestr);
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//加载用户信息
	public void loadInfoMes(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		UserInfo uif = UserInfo.dao.findById(orderStr);
		json.put("money", uif.getDouble("fd_money"));
		json.put("icemoney", uif.getDouble("fd_icemoney"));
		json.put("applymoney", uif.getDouble("fd_applymoney"));
		json.put("username", uif.getStr("fd_username"));
		json.put("truename", uif.getStr("fd_truename"));
		json.put("phone", uif.getStr("fd_phone"));
		json.put("bank", uif.getStr("fd_bank"));
		json.put("creatime", uif.getDate("fd_creatime"));
		json.put("status", uif.getStr("fd_status"));
		json.put("IDcase", uif.getStr("fd_IDcase"));
		json.put("tjUser", uif.getStr("fd_tjUser"));
		renderJson(json.toJSONString());
	}
	
	public void upinfoMes(){
		String orderStr = getPara("onu");
		String IDcase = getPara("IDcase");
		String bank = getPara("bank");
		String phone = getPara("phone");
		String status = getPara("status");
		double applymoney = Double.parseDouble(getPara("applymoney"));
		double icemoney = Double.parseDouble(getPara("icemoney"));
		double money = Double.parseDouble(getPara("money"));
		String truename = getPara("truename");
		String username = getPara("username");
		UserInfo uif = UserInfo.dao.findById(orderStr);
		uif.set("fd_IDcase", IDcase);
		uif.set("fd_bank", bank);
		uif.set("fd_phone", phone);
		uif.set("fd_status", status);
		uif.set("fd_applymoney", applymoney);
		uif.set("fd_icemoney", icemoney);
		uif.set("fd_money", money);
		uif.set("fd_truename", truename);
		uif.set("fd_username", username);
		JSONObject json = new JSONObject();
		if(uif.update()){
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
	
	//校验登录密码
	public void checklogUser(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String checkPass = getPara("checkPass");
		checkPass = MD5Util.md5(checkPass);
		UserInfo uif = UserInfo.dao.findById(orderStr);
		if(checkPass.equals(uif.getStr("fd_password"))){
			json.put("status", 1);
		}else{
			json.put("status", 0);
		}
		renderJson(json.toJSONString());
	}

	//校验登录密码
	public void checkzhifUser(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String checkPass = getPara("checkPass");
		checkPass = MD5Util.md5(checkPass);
		UserInfo uif = UserInfo.dao.findById(orderStr);
		if(checkPass.equals(uif.getStr("fd_paypassword"))){
			json.put("status", 1);
		}else{
			json.put("status", 0);
		}
		renderJson(json.toJSONString());
	}
	
	//重置登录密码
	public void reslogUser(){
		String orderStr = getPara("onu");
		UserInfo uif = UserInfo.dao.findById(orderStr);
		uif.set("fd_password", "e10adc3949ba59abbe56e057f20f883e");
		JSONObject json = new JSONObject();
		if(uif.update()){
			json.put("status", 1);
		}else{
			json.put("status", 0);
		}
		renderJson(json.toJSONString());
	}
	
	//重置支付密码
	public void reszhifUser(){
		String orderStr = getPara("onu");
		UserInfo uif = UserInfo.dao.findById(orderStr);
		uif.set("fd_paypassword", "e10adc3949ba59abbe56e057f20f883e");
		JSONObject json = new JSONObject();
		if(uif.update()){
			json.put("status", 1);
		}else{
			json.put("status", 0);
		}
		renderJson(json.toJSONString());
	}
	
	//加载全部用户供下拉框使用
	public void loaduseinf(){
		JSONArray ja = new JSONArray();
		List<UserInfo> userlist = UserInfo.dao.find("select fd_username,id from userinfo order by fd_creatime desc");
		for(UserInfo uif : userlist){
			JSONObject json = new JSONObject();
			json.put("rname", uif.getStr("fd_username"));
			json.put("rid", uif.getStr("id"));
			ja.add(json);
		}
		renderJson(ja.toString());
	}
	
	//开始定时器
	/*public void startTest(){
		JSONObject json = new JSONObject();
		QuzarTimer.getInstance().Quzar();
		json.put("status", 1);
		renderJson(json.toJSONString());
	}*/
}