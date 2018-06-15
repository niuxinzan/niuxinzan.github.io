package cn.com.nxz.sparkStudy;

import java.util.ArrayList;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;

import cn.com.cennavi.util.FileUtil;
import cn.com.nxz.conf.SparkConfigSington;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 *  O\ = /O
 * ___/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 *          .............................................
 *           佛曰：bug泛滥，我已瘫痪！
 */
public class TestHdfsRead {
	
	private static String logFile = "hdfs://nujhadoop/test/dhy/TIDAL_LANE/BEIJING/HourFilling/part-r-00000";
	private static String outFile = "file:///d:/data/spark2-0-1.csv";
	private static String hadoopFile="d:\\hadoop-2.4.0";
	
	private static SparkConf sparkconf = new SparkConf();
	
	public static void main(String[] args) {
		int initResult = new TestHdfsRead().init();
		if (initResult == 0) {
			System.out.println("init mathod failure!");
			System.exit(0);
		}

		JavaSparkContext sc = new JavaSparkContext(sparkconf);
		JavaRDD<String> logData = sc.textFile(logFile).cache();

		JavaRDD<String> result = logData.map(new Function<String, String>() {

			public String call(String line) throws Exception {
				// TODO Auto-generated method stub
				System.out.println(line);
				return line;
			}
		});
		result.saveAsTextFile(outFile);

	}

	public int init() {
		try {
			System.setProperty("hadoop.home.dir",hadoopFile);
			SparkConfigSington props = SparkConfigSington.getInstance();
			ArrayList<String> list = FileUtil.quietly(props.getValue("spark.jars"),
					new ArrayList<String>());
			sparkconf.setJars(list.toArray(new String[list.size()]));
			sparkconf.setAppName(props.getValue("spark.appName"));
			sparkconf.setMaster(props.getValue("spark.master"));
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}

	

}
