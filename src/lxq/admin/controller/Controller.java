package lxq.admin.controller;

import com.alibaba.fastjson.JSONObject;
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
		JSONObject json = new JSONObject();
		if("".equals(getPara("rname"))||null==getPara("rname")){
			render("/admin/EasyUI/login.html");
			return;
		}
		if(new FormString().userLogin(getPara("rname"), getPara("ssword"))){
			setSessionAttr("loginAdmin", getPara("rname"));
			json.put("state", "succed");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	//�˻���¼
	public void loginout(){
		removeSessionAttr("loginAdmin");
		render("/admin/EasyUI/login.html");
	}
	
}
