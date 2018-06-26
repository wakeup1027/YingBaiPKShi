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
		UserInfo uinfo = GetUserInfo.CheckUser(getPara("userName"), getPara("password"));
		if(uinfo!=null){
			setSessionAttr("loginUser", uinfo.get("fd_username"));
			setSessionAttr("UserId", uinfo.get("fd_id"));
			setSessionAttr("Password", uinfo.get("fd_password"));
			redirect("/VipCustomer.html");
		}else{
			render("/admin/LayUI/login.html");
		}
	}
	
	//申请注册界面
	public void regit(){
		render("/admin/LayUI/regit.html");
	}
	
	//保存用户申请信息
	public void ApplyRegit(){
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		UserInfo model = getModel(UserInfo.class,"userInfo");
		model.set("fd_id", uuid);
		model.set("fd_creatime",sdf.format(now));
		model.set("fd_status","0");
		boolean res = model.save();
		JSONObject json = new JSONObject();
		if(res){
			json.put("status", "200");
		}else{
			json.put("status", "500");
		}
		renderJson(json.toJSONString());
	}
	
	//退户登录
	public void loginout(){
		removeSessionAttr("loginUser");
		render("/admin/LayUI/login.html");
	}
	
}
