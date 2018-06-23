package lxq.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * �������ӿ�����
 * @author LXQ
 * 
 */
public class KaiCaiDateUntil {
	public static void main(String[] args) {
		//��ʽ��������������
		String url = "http://r.apiplus.net/newly.do?token=d5b129407dbac0d0f4d7d6c689c55dd1&code=bjpk10&rows=5&format=json&date=2015-02-18";
		//���Ի�������������
		String url1 = "http://t.apiplus.net/daily.do?code=bjpk10&format=json&date=2018-06-20";
		String urlAll = new StringBuffer(url).toString();
		String charset = "UTF-8";
		String jsonResult = get(urlAll, charset);// �õ�JSON�ַ���
		System.out.println(jsonResult);
		/*JSONObject object = JSONObject.fromObject(jsonResult);// ת��ΪJSON��
		try {
			Iterator it = object.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = object.getString(key);
				JSONObject object1 = JSONObject.fromObject(value);// ת��ΪJSON��
				String outputStr = "id:" + key;
				outputStr += " number:" + object1.getString("number");
				outputStr += " dateline:" + object1.getString("dateline");
				System.out.println(outputStr);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}*/

	}

	/**
	 * 
	 * @param urlAll:����ӿ�
	 * @param charset:�ַ�����
	 * @return ����json���
	 */
	public static String get(String urlAll, String charset) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";// ģ�������
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
