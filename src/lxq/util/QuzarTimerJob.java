package lxq.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.BetsDataLog;
import com.bean.Message;
import com.bean.OpenNumber;
import com.bean.SecondTable;
import com.bean.UserInfo;

public class QuzarTimerJob implements Job{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String token = "t10e9c565ded1e473k";
	private static final String code = "bjpk10";
	private static final String rows = "1";
	private static final String format = "json";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String url = "http://ho.apiplus.net/newly.do?";
		url += "token="+token+"&";
		url += "code="+code+"&";
		url += "rows="+rows+"&";
		url += "format="+format;
		String urlAll = new StringBuffer(url).toString();
		String charset = "UTF-8";
		String jsonResult = KaiCaiDateUntil.getHttpDate(urlAll, charset);
		JSONObject jsonObject = JSON.parseObject(jsonResult);
		String dataStr = jsonObject.getString("data");
		JSONArray jsonArray = JSON.parseArray(dataStr);
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject obj = jsonArray.getJSONObject(i);
			OpenNumber on = OpenNumber.dao.findFirst("SELECT fd_id FROM opennumber WHERE fd_qishu = "+obj.getString("expect"));
			if(null==on){ //������Ϊ�գ���˵����û�����ĺ���
				OpenNumber openNum = new OpenNumber();
				String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				openNum.set("fd_id", uuid);
				openNum.set("fd_qishu", obj.getString("expect"));
				openNum.set("fd_number", obj.getString("opencode"));
				openNum.set("fd_creatime", obj.getString("opentime"));
				openNum.set("fd_timestamp", obj.getString("opentimestamp"));
				boolean yessave = openNum.save();
				if(yessave){
					SecondTable st = SecondTable.dao.findById("857bef8a26ba4e97aa5550c4072fdebe");
					st.set("second", 300);
					st.update();
					
					//��ʼͳ���н�����
					IsWin(obj.getString("expect"),obj.getString("opencode"));
				}
			}
		}
	}
	
	
	//��֤��ע�������Ƿ����н�
	public static void IsWin(String qiNum,String openumber){
		List<BetsDataLog> blog = BetsDataLog.dao.find("SELECT * FROM betsdatalog WHERE fd_qishu = '"+qiNum+"'");
		String openum[] = openumber.split(",");
		for(BetsDataLog bd : blog){
			UserInfo uif = UserInfo.dao.findById(bd.getStr("fd_userid"));
			int s = Integer.parseInt(bd.getStr("fd_type"));
			String sd = openum[s-1];
			String sds = bd.getStr("fd_num");
			if(sd.equals(sds)){
				bd.set("fd_iswin", "1");  //Ӯ
				uif.set("fd_money", uif.getInt("fd_money")+(bd.getInt("fd_tatol")*10)); //����
				Message ms = new Message();
				String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				Date now = new Date();
				ms.set("id", uuid);
				ms.set("fd_title", "��ϵͳ��Ϣ����ϲ��Ӯȡ�˵�"+bd.getStr("fd_qishu")+"��"+FROMTYPE(bd.getStr("fd_type"))+"����Ϊ"+bd.getStr("fd_num")+"��ע��");
				ms.set("fd_creatime", sdf.format(now));
				ms.set("fd_connet", "��ϵͳ��Ϣ����ϲ��Ӯȡ�˵�"+bd.getStr("fd_qishu")+"��"+FROMTYPE(bd.getStr("fd_type"))+"����Ϊ"+bd.getStr("fd_num")+"��ע��");
				ms.set("fd_type", "0");
				ms.set("fd_ready", "1");
				ms.set("fd_senduser", bd.getStr("fd_userid"));
				ms.save();
			}else{
				bd.set("fd_iswin", "0");  //��
				uif.set("fd_money", uif.getInt("fd_money")-bd.getInt("fd_tatol")); //��û�б���
			}
			if(bd.update()){
				uif.update();
			}
		}
	}
	
	public static String FROMTYPE(String value){
		if(value.equals("1")){
			return "�ھ�";
		}else if(value.equals("2")){
			return "�Ǿ�";
		}else if(value.equals("3")){
			return "����";
		}else if(value.equals("4")){
			return "������";
		}else if(value.equals("5")){
			return "������";
		}else if(value.equals("6")){
			return "������";
		}else if(value.equals("7")){
			return "��7��";
		}else if(value.equals("8")){
			return "��8��";
		}else if(value.equals("9")){
			return "��9��";
		}else if(value.equals("10")){
			return "��10��";
		}else{
			return "δ֪����";
		}
	}

}