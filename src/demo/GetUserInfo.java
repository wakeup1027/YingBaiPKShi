package demo;

import com.bean.UserInfo;

import lxq.util.MD5Util;

public class GetUserInfo {

	public static UserInfo CheckUser(String username, String password){
		if(null!=password){
			password = MD5Util.md5(password);
		}
		String sqlStr = "SELECT * FROM userinfo WHERE fd_username = '"+username+"' AND fd_password = '"+password+"' AND fd_status = '1' LIMIT 1";
		UserInfo umesList = new UserInfo().findFirst(sqlStr);
		if(umesList == null){  
            return null;
        }
		return umesList;
	}
}
