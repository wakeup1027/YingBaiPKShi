package lxq.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ��ʽ�������
 * @author Administrator
 *
 */
public class FormString {
	
	//�ж��û���½��֤
	public boolean userLogin(String user, String password){
		if("a66667777".equals(user)&&"661bb520dc12b6e94d0bff5136569414".equals(MD5Util.md5(password))){
			return true;
		}else{
			return false;
		}
	}

	//�û�����չʾ*���ŵķ���
	public static String formstringstart(int num, String forstr){
		if("".equals(forstr)){
			return "";
		}
		String s = forstr.substring(0,num);
		String str = "";
		for(int i=0; i<forstr.length()-num; i++){
			str+="*";
		}
		return s+str;
	}
	
	//У�鲻�����������ַ��ķ���
	public static boolean specialfyns(String keyvale){
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keyvale);
        return m.find();
	} 
	
}
