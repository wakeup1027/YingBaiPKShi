package lxq.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.UserInfo;
import com.bean.UserInfoLog;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import demo.AdminInterceptor;
import lxq.util.QuzarTimer;

@Before(AdminInterceptor.class)
@ControllerBind(controllerKey = "/AdminStrUrl")
public class EasyUI_Controller extends BaseController{
	
	//首页
	public void index(){
		render("/admin/EasyUI/index.html");
	}
	
	//提现管理
	public void pucashPage(){
		render("/admin/EasyUI/pucash_page.html");
	}
	
	//用户中奖查看
	public void userwinPage(){
		render("/admin/EasyUI/userwin_page.html");
	}
	
	//历史下注记录
	public void historyPage(){
		render("/admin/EasyUI/history_page.html");
	}
	
	//用户管理
	public void userInfoPage(){
		render("/admin/EasyUI/userInfo_page.html");
	}
	
	//用户界面操作动作
	public void loaduserd(){
		Map<String, Object> map = new HashMap<String, Object>();
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");
		List<UserInfo> UI = UserInfo.dao.findByPage(page, rows, "");
		/*List<UserInfoLog> luilog = new ArrayList<UserInfoLog>();
		for(UserInfo uin : UI){
			UserInfoLog ulog = new UserInfoLog();
			ulog.setId(uin.getStr("id"));
			ulog.setFd_username(uin.getStr("fd_username"));
			ulog.setFd_truename(uin.getStr("fd_truename"));
			ulog.setFd_money(uin.getInt("fd_money"));
			ulog.setFd_icemoney(uin.getInt("fd_icemoney"));
			ulog.setFd_applymoney(uin.getInt("fd_applymoney"));
			ulog.setFd_phone(uin.getStr("fd_phone"));
			ulog.setFd_IDcase(uin.getStr("fd_IDcase"));
			ulog.setFd_bank(uin.getStr("fd_bank"));
			ulog.setFd_status(uin.getStr("fd_status"));
			ulog.setFd_tjUser(uin.getStr("fd_tjUser"));
			ulog.setFd_creatime(uin.getStr("fd_creatime"));
			luilog.add(ulog);
		}*/
		
		Long total = UserInfo.dao.count("SELECT * FROM userinfo");
		map.put("rows", UI);
	    map.put("total", total); 
		renderJson(map);
		
	}
	
	//开始定时器
	public void startTest(){
		JSONObject json = new JSONObject();
		QuzarTimer.getInstance().Quzar();
		json.put("status", 1);
		renderJson(json.toJSONString());
	}
}