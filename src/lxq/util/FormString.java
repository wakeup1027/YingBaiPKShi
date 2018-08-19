package lxq.util;

import java.util.Random;

/**
 * 格式化结果集
 * @author Administrator
 *
 */
public class FormString {
	private static final String[] StrNum= {"1","2","3","4","5","6"};//随机开奖
	/*private static final String[] Small = {"111","112","113","114","115","116","122","123","124","125","126","133","134","135","136","144","145","222","223","224","225","226","233","234","235","244","333","334"};
	private static final String[] Bigger ={"146","155","156","166","236","245","246","255","256","266","335","336","344","345","346","355","356","366","444","445","446","455","456","466","555","556","566","666"};
	private static final String[] Single ={"112","114","116","122","124","126","134","136","144","222","224","226","234","244","334","146","156","166","236","246","256","266","336","344","346","356","366","444","446","456","466","556","566","666"};
	private static final String[] Double ={"111","113","115","123","125","133","135","145","223","225","233","235","333","155","245","255","335","345","355","445","455","555"};*/
	//获取第一个数
	public String firstNum(String Num){
		String StrNum = "";
		switch (Num.substring(0,1)) {
		case "1":
			StrNum = "0";
			break;
		case "2":
			StrNum = "-42";
			break;
		case "3":
			StrNum = "-84";
			break;
		case "4":
			StrNum = "-126";
			break;
		case "5":
			StrNum = "-168";
			break;
		case "6":
			StrNum = "-210";
			break;
		default:
			StrNum = "0";
			break;
		}
		return StrNum;
	}
	
	//获取第二位数
	public String secondNum(String Num){
		String StrNum = "";
		switch (Num.substring(1,2)) {
		case "1":
			StrNum = "0";
			break;
		case "2":
			StrNum = "-42";
			break;
		case "3":
			StrNum = "-84";
			break;
		case "4":
			StrNum = "-126";
			break;
		case "5":
			StrNum = "-168";
			break;
		case "6":
			StrNum = "-210";
			break;
		default:
			StrNum = "0";
			break;
		}
		return StrNum;
	}
	
	//获取第三位数
	public String threeNum(String Num){
		String StrNum = "";
		switch (Num.substring(2,3)) {
		case "1":
			StrNum = "0";
			break;
		case "2":
			StrNum = "-42";
			break;
		case "3":
			StrNum = "-84";
			break;
		case "4":
			StrNum = "-126";
			break;
		case "5":
			StrNum = "-168";
			break;
		case "6":
			StrNum = "-210";
			break;
		default:
			StrNum = "0";
			break;
		}
		return StrNum;
	}
	
	//求大小
	public String bigorsam(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)<=10){
			return "小";
		}else{
			return "大";
		}
	}
	
	//求单双
	public String onlyAll(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)%2==0){
			return "双";
		}else{
			return "单";
		}
	}
	
	//求和
	public int allNum(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		return (f+s+t);
	}
	
	//随机获取1~6的三位数
	public String getThreeNum(){
		Random random = new Random();
		int[] arr1 = new int[3];
		for(int i=0; i<3; i++){
			int index = random.nextInt(6);
			arr1[i]=Integer.parseInt(StrNum[index]);
		}
		return Paixu(arr1);
	}
	
	public String Paixu(int[] num){
		int temp = 0;
        int size = num.length;
        for(int i = 0 ; i < size-1; i ++){
        	for(int j = 0 ;j < size-1-i ; j++){
        		if(num[j] > num[j+1]){  //交换两数位置
        			temp = num[j];
        			num[j] = num[j+1];
        			num[j+1] = temp;
        		}
        	}
        }
        return String.valueOf(num[0])+String.valueOf(num[1])+String.valueOf(num[2]);
	}
	
	//补位，如果位数不足的时候用0来填上
	public String formNum(int nowday){
		String strnowday = String.valueOf(nowday);
		String zero = "";
		for(int i=0; i<(5-strnowday.length()); i++){
			zero+="0";
		}
		return zero+strnowday;
	}
	
	//判断用户登陆验证
	public boolean userLogin(String user, String password){
		if("admin".equals(user)&&"123456".equals(password)){
			return true;
		}else{
			return false;
		}
	}
	
	public int subStr(String num){
		String numStr = num.substring(4, num.length());
		int numInt = Integer.parseInt(numStr);
		return numInt;
	}
	
	public String subStrTwo(String num,int star){
		return num.substring(star, num.length());
	}
	
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
	
	public static void main(String[] args) { 
		  System.out.println(new FormString().formNum(10000));
	}
	
}
