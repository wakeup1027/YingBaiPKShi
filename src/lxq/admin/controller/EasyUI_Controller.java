package lxq.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
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
	
	//��ʼ��ʱ��
	public void startTest(){
		JSONObject json = new JSONObject();
		QuzarTimer.getInstance().Quzar();
		json.put("status", 1);
		renderJson(json.toJSONString());
	}
}