package lxq.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.UserInfo;
import com.config.ControllerBind;

import demo.GetUserInfo;

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
		JSONObject json = new JSONObject();
		if("".equals(getPara("rname"))||null==getPara("rname")){
			render("/admin/LayUI/login.html");
			return;
		}
		UserInfo uinfo = GetUserInfo.CheckUser(getPara("rname"), getPara("ssword"));
		if(uinfo!=null){
			setSessionAttr("loginUser", uinfo.getStr("fd_username"));
			setSessionAttr("UserId", uinfo.getStr("id"));
			//setSessionAttr("Password", uinfo.get("fd_paypassword"));
			json.put("state", "succed");
		}else{
			json.put("state", "error");
			//redirect("/VipCustomer.html");
		}
		renderJson(json.toJSONString());
	}
	
	//退户登录
	public void loginout(){
		removeSessionAttr("loginUser");
		render("/admin/LayUI/login.html");
	}
	
}
