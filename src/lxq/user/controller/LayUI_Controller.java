package lxq.user.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.BetsDataLog_Bean;
import com.bean.ApplyMoneyLog_Bean;
import com.bean.ApplyMoneyLog;
import com.bean.BetsDataLog;
import com.bean.Recharge;
import com.bean.Recharge_Bean;
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
		render(HomePathPage+"apply_page.html");
	}
	
	//�û�����
	public void userCenter(){
		render(HomePathPage+"user_center.html");
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
