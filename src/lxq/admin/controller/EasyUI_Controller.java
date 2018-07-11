package lxq.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.UserInfo;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import demo.AdminInterceptor;
import lxq.util.QuzarTimer;

@Before(AdminInterceptor.class)
@ControllerBind(controllerKey = "/AdminStrUrl")
public class EasyUI_Controller extends BaseController{
	
	//��ҳ
	public void index(){
		render("/admin/EasyUI/index.html");
	}
	
	//���ֹ���
	public void pucashPage(){
		render("/admin/EasyUI/pucash_page.html");
	}
	
	//�û��н��鿴
	public void userwinPage(){
		render("/admin/EasyUI/userwin_page.html");
	}
	
	//��ʷ��ע��¼
	public void historyPage(){
		render("/admin/EasyUI/history_page.html");
	}
	
	//�û�����
	public void userInfoPage(){
		render("/admin/EasyUI/userInfo_page.html");
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
			UserInfo uif = new UserInfo(); //UserInfo.dao.findById(sd);
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
	public void startTest(){
		JSONObject json = new JSONObject();
		QuzarTimer.getInstance().Quzar();
		json.put("status", 1);
		renderJson(json.toJSONString());
	}
}