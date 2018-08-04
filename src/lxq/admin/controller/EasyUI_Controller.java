package lxq.admin.controller;

import java.io.File;
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
import com.bean.SecondTable;
import com.bean.UserInfo;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.upload.UploadFile;

import demo.AdminInterceptor;

@Before(AdminInterceptor.class)
@ControllerBind(controllerKey = "/AdminStrUrl")
public class EasyUI_Controller extends BaseController{
	
	//��ҳ
	public void index(){
		//�鿴������������
		OpenNumber onum = OpenNumber.dao.findFirst("SELECT * FROM opennumber ORDER BY fd_qishu DESC");
		List<BetsDataLog> btlist = BetsDataLog.dao.find("SELECT * FROM betsdatalog WHERE fd_qishu = '"+(onum.getInt("fd_qishu")+1)+"'");
		setAttr("btlist",btlist);
		setAttr("opennum",onum.getInt("fd_qishu")+1);
		setAttr("montotal",BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_qishu = '"+(onum.getInt("fd_qishu")+1)+"'"));
		render("/admin/EasyUI/index.html");
	}
	
	//��ֵ����
	public void rechargePage(){
		render("/admin/EasyUI/recharge.html");
	}
	
	//���ֹ���
	public void pucashPage(){
		render("/admin/EasyUI/pucash_page.html");
	}
	
	//�û��н��鿴
	public void userwinPage(){
		render("/admin/EasyUI/userwin_page.html");
	}
	
	//��Ӯ�鿴
	public void historyPage(){
		render("/admin/EasyUI/history_page.html");
	}
	
	//�û�����
	public void userInfoPage(){
		render("/admin/EasyUI/userInfo_page.html");
	}
	
	//ϵͳ����
	public void systemSet(){
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		setAttr("st",st);
		render("/admin/EasyUI/system_set.html");
	}
	
	//�޸�����
	public void uplost(){
		JSONObject json = new JSONObject();
		double s = getParaToInt("nom");
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("lostnum", s);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//�޸ĵ���ʱ
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
	
	//����΢�Ŷ�ά��
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
		//��¼ͼƬ·��д��
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("wachat", "upload/"+newfilename);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//����֧������ά��
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
		//��¼ͼƬ·��д��
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		st.set("zhifb", "upload/"+newfilename);
		if(st.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json);
	}
	
	//��ֵ�����������
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
	
	//��ֵ���Ҽ�������
	public void findrecherge(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWord = getPara("keyowl");
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<Recharge> UI = Recharge.dao.findByPage("WHERE fd_username='"+keyWord+"'", page, rows,"fd_creatime");
		Long total = Recharge.dao.count("SELECT * FROM recharge WHERE fd_username='"+keyWord+"'");
		map.put("taoslm", Recharge.dao.findFirst("SELECT SUM(fd_money) AS total FROM recharge WHERE fd_username='"+keyWord+"'"));
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//�޸ĳ�ֵ״̬
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
	
	//�������ּ�¼����
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
	
	//���ز������ּ�¼����
	public void findpucashPage(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWord = getPara("keyowl");
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<ApplyMoney> UI = ApplyMoney.dao.findByPage("WHERE fd_username='"+keyWord+"'", page, rows,"fd_status");
		Long total = ApplyMoney.dao.count("SELECT * FROM applymoney WHERE fd_username='"+keyWord+"'");
		//��ȡ�����û��������ܽ��
	    map.put("taoslm", ApplyMoney.dao.findFirst("SELECT SUM(fd_money) AS total FROM applymoney WHERE fd_username='"+keyWord+"'"));
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//�޸�����״̬
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
	
	//�����û���ע��Ϣ
	public void loadwindate(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<BetsDataLog> UI = BetsDataLog.dao.findByPage(page, rows, "ORDER BY fd_qishu DESC");
		Long total = BetsDataLog.dao.count("SELECT * FROM betsdatalog");
		map.put("rows", UI);
	    map.put("total", total); 
	    //��ȡ�û�Ӯ�ļ�¼
	    map.put("win", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_iswin = '1'"));
	    //��
	    map.put("fualt", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_iswin = '0'"));
	    //δ����
	    map.put("onopen", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog WHERE fd_iswin = '2'"));
	    renderJson(map);
	}
	
	//�����û���ע��Ϣ
	public void findwindate(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWordNum = getPara("keyowlnum");
		String keyWordUsm = getPara("keyowlusm");
		String strtm = getPara("strtm");
		String overm = getPara("overm");
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
		List<BetsDataLog> UI = BetsDataLog.dao.findByPage(wherestr, page, rows,"fd_iswin");
		Long total = BetsDataLog.dao.count("SELECT * FROM betsdatalog "+wherestr);
		map.put("rows", UI);
		map.put("total", total); 
		//��ȡ�û�Ӯ�ļ�¼
		map.put("win", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog "+wherestr+" AND fd_iswin = '1'"));
		//��
		map.put("fualt", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog "+wherestr+" AND fd_iswin = '0'"));
		//δ����
		map.put("onopen", BetsDataLog.dao.findFirst("SELECT SUM(fd_tatol) AS total FROM betsdatalog "+wherestr+" AND fd_iswin = '2'"));
		renderJson(map);
	}
	
	//�û������������
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
	
	//�û������������
	public void finduserd(){
		Map<String, Object> map = new HashMap<String, Object>();
		String keyWordUsm = getPara("keyowl");
		String wherestr = "WHERE fd_username='"+keyWordUsm+"'";
		if(keyWordUsm.equals("")){
			wherestr = "";
		}
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<UserInfo> UI = UserInfo.dao.findByPage(wherestr, page, rows, "fd_creatime");
		Long total = UserInfo.dao.count("SELECT * FROM userinfo "+wherestr);
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//�����û�
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
				System.out.println("�����û�����ʧ�ܣ�");
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//�����û�
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
				System.out.println("�����û�����ʧ�ܣ�");
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//�����û�
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
				System.out.println("�����û�����ʧ�ܣ�");
			}
		}
		if(doUp){
			json.put("status", 0);
		}else{
			json.put("status", 1);
		}
		renderJson(json.toJSONString());
	}
	
	//��ʼ��ʱ��
	/*public void startTest(){
		JSONObject json = new JSONObject();
		QuzarTimer.getInstance().Quzar();
		json.put("status", 1);
		renderJson(json.toJSONString());
	}*/
}