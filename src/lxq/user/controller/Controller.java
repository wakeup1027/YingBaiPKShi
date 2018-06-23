package lxq.user.controller;

import com.base.BaseController;
import com.config.ControllerBind;

import lxq.util.FormString;

@ControllerBind(controllerKey = "/")
public class Controller extends BaseController {
	
	/**知识点
	 * setAttr("dateList",list);
	 * getParaToInt("time")
	 * JSONObject json = new JSONObject();
	 * renderJson(json.toJSONString());
	 */
	public void index(){
		render("/admin/LayUI/login.html");
	}
	
	//登录用户
	public void login(){
		if(new FormString().userLogin(getPara("userName"), getPara("password"))){
			setSessionAttr("loginUser", getPara("userName"));
			redirect("/VipCustomer.html");
		}else{
			render("/admin/LayUI/login.html");
		}
	}
	
	//退户登录
	public void loginout(){
		removeSessionAttr("loginUser");
		render("/admin/LayUI/login.html");
	}
	
}
