package lxq.util;

import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.OpenNumber;
import com.bean.SecondTable;

public class QuzarTimerJob implements Job{
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
			if(null==on){
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
				}
			}
		}
	}

}