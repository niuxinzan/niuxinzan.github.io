package cn.com.nxz.conf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 读取spark配置文件
 * @author cennavi
 *
 */
public class SparkConfigSington {
	private Properties pros = null;
	private String configFile="conf/sparkConfig.properties";
	
	public static SparkConfigSington getInstance(){
		return ConfigurationHolder.configuration; 
	}
	
	public String getValue(String key){  
        return pros.getProperty(key);  
    }  
	
	private static class ConfigurationHolder{  
        private static SparkConfigSington configuration = new SparkConfigSington();  
    }  

	private SparkConfigSington() {
		readConfig();
	}

	private void readConfig() {
		pros = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(Thread.currentThread()
					.getContextClassLoader().getResource("").getPath()
					+ configFile);
			pros.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
