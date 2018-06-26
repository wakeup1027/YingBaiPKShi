package demo;

import com.bean.UserInfo;

public class GetUserInfo {

	public static UserInfo CheckUser(String username, String password){
		String sqlStr = "SELECT * FROM userinfo WHERE fd_username = '"+username+"' AND fd_password = '"+password+"' AND fd_status = '1' LIMIT 1";
		UserInfo umesList = new UserInfo().findFirst(sqlStr);
		if(umesList == null){  
            return null;
        }
		return umesList;
	}
}
