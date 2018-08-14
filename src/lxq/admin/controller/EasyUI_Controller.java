package lxq.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.Answear;
import com.bean.ApplyMoney;
import com.bean.BetsDataLog;
import com.bean.KefuMes;
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
		List<Recharge> UI = Recharge.dao.findByPage(page, rows, "");
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
			String usid = uif.getStr("fd_userid");
			UserInfo useif = UserInfo.dao.findById(usid);
			useif.set("fd_money", useif.getDouble("fd_money")+uif.getDouble("fd_money"));
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
	
	//修改充值状态成功
	public void upsrecha(){
		JSONObject json = new JSONObject();
		String orderStr = getPara("onu");
		String[] ords = orderStr.split(",");
		boolean doUp = false;
		for(String sd : ords){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			Recharge uif = Recharge.dao.findById(sd);
			uif.set("fd_arraytime", sdf.format(now));
			uif.set("fd_status", "2");
			uif.set("fd_commit", "系统暂未收到付款，请认真核对支付单号再重新发起一遍，如果有误，请联系客服通过其他方式确认！");
			try {
				uif.update();
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
			String usid = uif.getStr("fd_userid");
			uif.set("fd_arraytime", sdf.format(now));
			uif.set("fd_status", upStutas);
			uif.set("fd_failreason", failreason);
			try {
				boolean yesavs = uif.update();
				if(yesavs&&upStutas.equals("2")){//提现完成才要把用户信息中的提现金额清空
					UserInfo useif = UserInfo.dao.findById(usid);
					useif.set("fd_applymoney", 0);
					useif.update();
				}else if(yesavs&&upStutas.equals("3")){//如果提现失败，则退回提现金额到账户余额里面
					UserInfo useif = UserInfo.dao.findById(usid);
					useif.set("fd_money", useif.getDouble("fd_money")+useif.getDouble("fd_applymoney"));
					useif.set("fd_applymoney", 0);
					useif.update();
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