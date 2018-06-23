package lxq.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class Config {
	
	private Properties prop = new Properties();
	private static Config instance = new Config();
	
	private Config() {
		loadCfg();
	}
	
	//加载配置文件
	private void loadCfg() {
		try {
			String resource = "/SystemConfig.properties";
			boolean hasLeadingSlash = resource.startsWith("/");
			String stripped = hasLeadingSlash ? resource.substring(1): resource;
			InputStream in = null;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader != null) {
				in = classLoader.getResourceAsStream(resource);
				if (in == null && hasLeadingSlash) {
					in = classLoader.getResourceAsStream(stripped);
				}
			}

			if (in == null) {
				in = Config.class.getClassLoader().getResourceAsStream(resource);
			}
			if (in == null && hasLeadingSlash) {
				in = Config.class.getClassLoader().getResourceAsStream(stripped);
			}

			prop.load(in);
			in.close();
			in = null;
			
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取UnifyPendCfg配置文件出错!");
		}
	}

	public String getConfigValue(String configName) {
		return getConfigValue(configName, "");
	}

	public String getConfigValue(String configName, String defaultValue) {
		String propValue = prop.getProperty(configName, defaultValue);
		if (propValue != null) {
			propValue = propValue.trim();
		}
		return propValue;
	}

	public static Config getInstance() {
		return instance;
	}
	

	/**
	 * @param 
	 */
	public static void main(String[] args) {
		Config config = getInstance();
		System.out.println(config.getConfigValue("openTime"));
	}

}
