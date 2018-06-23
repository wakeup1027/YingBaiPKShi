package lxq.admin.controller;

import com.base.BaseController;
import com.config.ControllerBind;

import lxq.util.FormString;

@ControllerBind(controllerKey = "/alogin")
public class Controller extends BaseController {
	
	/**֪ʶ��
	 * setAttr("dateList",list);
	 * getParaToInt("time")
	 * JSONObject json = new JSONObject();
	 * renderJson(json.toJSONString());
	 */
	public void index(){
		render("/admin/EasyUI/login.html");
	}
	
	//��¼�û�
	public void login(){
		if(new FormString().userLogin(getPara("userName"), getPara("password"))){
			setSessionAttr("loginAdmin", getPara("userName"));
			redirect("/AdminStrUrl.html");
		}else{
			render("/admin/EasyUI/login.html");
		}
	}
	
	//�˻���¼
	public void loginout(){
		removeSessionAttr("loginAdmin");
		render("/admin/EasyUI/login.html");
	}
	
}
