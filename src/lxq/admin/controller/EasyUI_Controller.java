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
	
	//�������
	public void historyPage(){
		render("/admin/EasyUI/history_page.html");
	}
	
	//�û�����
	public void userInfoPage(){
		render("/admin/EasyUI/userInfo_page.html");
	}
	
	//�����������
	public void openNums(){
		render("/admin/EasyUI/kaijiangqi.html");
	}
	
	//�ͷ���Ϣ����
	public void kefuMess(){
		render("/admin/EasyUI/kefuxinxi.html");
	}
	
	//ϵͳ����
	public void systemSet(){
		SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
		setAttr("st",st);
		render("/admin/EasyUI/system_set.html");
	}
	
	//���ؿ�������
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
	
	//��ȫ����
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
	
	//ɾ�������ĺ���
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
	
	//���ҿ������
	public void findnum(){
		JSONObject json = new JSONObject();
		int qisjh = getParaToInt("onu");
		OpenNumber opb = OpenNumber.dao.findFirst("SELECT * FROM opennumber WHERE fd_qishu = "+qisjh);
		if(opb!=null){
			json.put("openqishu", opb.getInt("fd_qishu"));
			json.put("opennum", opb.getStr("fd_number"));
			json.put("opentime", opb.getDate("fd_creatime"));
		}else{
			json.put("openqishu", "δ�ҵ����");
			json.put("opennum", "δ�ҵ����");
			json.put("opentime", "δ�ҵ����");
		}
		renderJson(json.toJSONString());
	}
	
	//�޸�����
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
	
	//��������������
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
	
	//������������������
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
	
	//�޸Ĺ�������������
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
	
	//ɾ���������
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
	
	//�ͷ������������
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
	
	//ɾ���ͷ���Ϣ
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
	
	//��ϵ�ͷ����Ҽ�������
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
	
	//�û���������
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
	
	//��ֵ���Ҽ�������
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
	
	//�޸ĳ�ֵ״̬�ɹ�
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
	
	//�޸ĳ�ֵ״̬�ɹ�
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
			uif.set("fd_commit", "ϵͳ��δ�յ����������˶�֧�����������·���һ�飬�����������ϵ�ͷ�ͨ��������ʽȷ�ϣ�");
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
		//��ȡ�����û��������ܽ��
	    map.put("taoslm", ApplyMoney.dao.findFirst("SELECT SUM(fd_money) AS total FROM applymoney "+wherestr));
		map.put("rows", UI);
		map.put("total", total); 
		renderJson(map);
	}
	
	//�޸�����״̬
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
				if(yesavs&&upStutas.equals("2")){//������ɲ�Ҫ���û���Ϣ�е����ֽ�����
					UserInfo useif = UserInfo.dao.findById(usid);
					useif.set("fd_applymoney", 0);
					useif.update();
				}else if(yesavs&&upStutas.equals("3")){//�������ʧ�ܣ����˻����ֽ��˻��������
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