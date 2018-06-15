package cn.com.nxz.sparkStudy;

import java.util.ArrayList;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.*;

import cn.com.cennavi.util.FileUtil;
import cn.com.nxz.bean.DhyHourFilling;
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
public class TestSparkDataFrame {
	private static String appName = null;
	private static SparkConf sparkconf = null;
	private static String imputFile = null;
	private static String hadoopFile = null;

	private static int init() {
		try {			
			appName = "testDataFrame";
			hadoopFile = "d:/hadoop-2.4.0";
			System.setProperty("hadoop.home.dir", hadoopFile);
			imputFile = "hdfs://nujhadoop/test/dhy/TIDAL_LANE/BEIJING/HourFilling/p*";

			SparkConfigSington props = SparkConfigSington.getInstance();
			ArrayList<String> list = FileUtil.quietly(props.getValue("spark.jars"), new ArrayList<String>());
			sparkconf = new SparkConf();
			sparkconf.setJars(list.toArray(new String[list.size()]));
			sparkconf.setAppName(props.getValue("spark.appName"));
			sparkconf.setMaster(props.getValue("spark.master"));
			sparkconf.set("spark.sql.warehouse.dir", imputFile);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void main(String[] args) {
		try {
			int result = init();
			if (result == 0) {
				System.out.println("init mathod failure!");
				System.exit(0);
			}
			SparkSession.clearDefaultSession();
			SparkSession spark = SparkSession.builder().appName(appName).config(sparkconf).getOrCreate();
			
			
			JavaRDD<String> rdd1 = spark.sparkContext().textFile(imputFile, 1).toJavaRDD();
			JavaRDD<DhyHourFilling> rdd2 = rdd1.map(new Function<String, DhyHourFilling>() {
						private static final long serialVersionUID = 1L;

						public DhyHourFilling call(String v1) throws Exception {
							// TODO Auto-generated method stub
							DhyHourFilling data = new DhyHourFilling();
							String[] arr = v1.split("\t");
							data.setDate(arr[0]);
							data.setPattern(arr[1]);
							return data;
						}
					});
			Dataset<Row> dhyHourFilling = spark.createDataFrame(rdd2,DhyHourFilling.class);
			dhyHourFilling.createOrReplaceTempView("DhyHourFilling");
			dhyHourFilling.show();

			Dataset<Row> bIddf = spark.sql("select date from DhyHourFilling");
			bIddf.show();

			spark.stop();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("windows 版spark会报Failed to delete:***错误，可以忽略!");
		}
	}
}
