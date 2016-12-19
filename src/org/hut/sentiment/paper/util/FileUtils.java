package org.hut.sentiment.paper.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static BufferedReader getBr(String inputPath, String code) {
		File inputFile = null;
		InputStreamReader isr = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			inputFile = new File(inputPath);
			is = new FileInputStream(inputFile);
			isr = new InputStreamReader(is, code);
			br = new BufferedReader(isr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return br;
	}
    public static List<String> load(String file, String encoding) {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = getBr(file, encoding);
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line.trim());
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
	public static boolean string2File(String str, String outputPath,
			String code, boolean isAppend) {
		boolean b = false;
		if (isEmpty(outputPath)) {
			return b;
		}
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(outputPath);
			fos = new FileOutputStream(file, isAppend);
			osw = new OutputStreamWriter(fos, code);
			bw = new BufferedWriter(osw);
			bw.write(str);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return b;
	}
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}


}
