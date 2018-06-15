package cn.com.cennavi.util;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {
	/**
	 * 工具类最好禁止外部初始化
	 */
	private FileUtil(){
		
	}
	/**
	 * 循环遍历路径
	 */
	public static synchronized ArrayList<String> quietly(String dir, ArrayList<String> result) {
		// 目录不存在，返回
		try {
			File file = new File(dir);
			if (!file.exists()) {
				return result;
			}
			// 遍历路径
			if (file.isDirectory()) {
				File[] filelist = file.listFiles();
				for (File tempdir : filelist) {
					// 递归
					result = quietly(tempdir.toString(), result);
				}
			} else {
				if (file.toString().endsWith(".jar")) {
					result.add(file.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return result;
		}

		return result;
	}
}
