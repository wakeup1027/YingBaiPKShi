package lxq.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.BetsDataLog_Bean;
import com.bean.ApplyMoneyLog_Bean;
import com.bean.ApplyMoney;
import com.bean.ApplyMoneyLog;
import com.bean.BetsDataLog;
import com.bean.Recharge;
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
	
	//��ҳ
	public void index(){
		render(HomePath+"home.html");
	}
	
	//��Ա��ֵ
	public void recharge(){
		render(HomePathPage+"recharge.html");
	}
	
	//��ע����
	public void xiazhuPage(){
		render(HomePathPage+"xiazhu_page.html");
	}
	
	//��ע��ʷ��¼����
	public void historyPage(){
		render(HomePathPage+"history_page.html");
	}
	
	//�������ֽ���
	public void applyPage(){
		String userid = getSessionAttr("UserId");
		ApplyMoney amoney = ApplyMoney.dao.findFirst("SELECT * FROM applymoney WHERE fd_userid = '"+userid+"' AND (fd_status = '0' OR fd_status = '1')");
		if(null!=amoney){
			setAttr("status",amoney.get("fd_status"));
		}else{
			setAttr("status","2");
		}
		render(HomePathPage+"apply_page.html");
	}
	
	//�û�����
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
			render("/");//���ص�¼ҳ���µ�¼
		}
	}
	
	//�û��޸��Լ�����Ϣ�����֤���룩 
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
	
	//�û��޸��Լ�����Ϣ���ֻ����룩
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
	
	//�û��޸��Լ�����Ϣ�����к��룩
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
	
	//��ֵ��¼
	public void rechargeLog(){
		render(HomePathPage+"recharge_log.html");
	}
	
	//���ּ�¼
	public void putCashLog(){
		render(HomePathPage+"putcash_log.html");
	}
	
	//δ����������ת
	public void inpNum(){
		render(HomePathPage+"kaijianNum.html");
	}
	
	//�������ֹ���
	public void ApplyVoid(){
		JSONObject json = new JSONObject();
		String userid = getSessionAttr("UserId");
		String agenPass = getPara("fdpassword");
		String truePass = getSessionAttr("Password");
		//�Ƚ������Ƿ���ȷ
		if(agenPass.equals(truePass)){
			UserInfo uifo = UserInfo.dao.findById(userid);
			int yuem = uifo.getInt("fd_money");//�˻����
			int getm = getParaToInt("cash");//�������ֽ��
			//�˻���������ڻ�����������
			if(yuem>=getm){
				int iceingm = uifo.getInt("fd_icemoney");//�˻����ڶ�������
				if(iceingm==0){
					//���˻����������ֽ��ȥ���ֽ����Ƕ�����
					uifo.set("fd_money", yuem-getm);
					uifo.set("fd_icemoney",getm);
					uifo.update();
					//������������
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
					json.put("mes", "���������������ĵȴ��ͷ����...");
					json.put("code", 200);
				}else{
					json.put("mes", "�˻����ڶ�����������������֣������ĵȴ��ͷ�����֮��������...");
					json.put("code", 502);
				}
			}else{
				json.put("mes", "�˻����㣡");
				json.put("code", 501);
			}
			
		}else{
			json.put("mes", "���벻��ȷ��");
			json.put("code", 500);
		}
		renderJson(json.toJSONString());
	}
	
	//��ֵ��¼����
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
	
	//��ע��¼
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
	
	//���ּ�¼
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
	
	//�����ѿ���������
	public void loadNoDate(){
		/*DateUtil DU = new DateUtil();
		String creantime = DU.getTime(dateStr, timeNum);*/
	}
	
	
}
