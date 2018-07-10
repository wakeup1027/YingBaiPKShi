package lxq.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.UserInfo;
import com.config.ControllerBind;
import com.jfinal.plugin.activerecord.Db;

import demo.GetUserInfo;
import lxq.util.FormString;

@ControllerBind(controllerKey = "/")
public class Controller extends BaseController {
	
	/**֪ʶ��
	 * setAttr("dateList",list);
	 * getParaToInt("time")
	 * JSONObject json = new JSONObject();
	 * renderJson(json.toJSONString());
	 */
	public void index(){
		render("/admin/LayUI/login.html");
	}
	
	//��¼�û�
	public void login(){
		UserInfo uinfo = GetUserInfo.CheckUser(getPara("userName"), getPara("password"));
		if(uinfo!=null){
			setSessionAttr("loginUser", uinfo.get("fd_username"));
			setSessionAttr("UserId", uinfo.get("id"));
			setSessionAttr("Password", uinfo.get("fd_password"));
			redirect("/VipCustomer.html");
		}else{
			render("/admin/LayUI/login.html");
		}
	}
	
	//�˻���¼
	public void loginout(){
		removeSessionAttr("loginUser");
		render("/admin/LayUI/login.html");
	}
	
}
