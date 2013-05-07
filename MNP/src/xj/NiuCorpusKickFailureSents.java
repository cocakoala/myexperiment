/**
 * 
 */
package xj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xj
 *
 */
public class NiuCorpusKickFailureSents {

	public static final String testFilePath = "/home/xj/Documents/experiment/testdata/test";
	public static final String testTreeFilePath = "/home/xj/Documents/experiment/testdata/test.tree";
	public static final String testOutPath = "/home/xj/Documents/experiment/testdata/test.filtered";
	public static final String testTreeOutPath = "/home/xj/Documents/experiment/testdata/test.tree.filtered";
	public static final String refFilePath = "/home/xj/Documents/experiment/testdata/ref";
	public static final String refOutPath = "/home/xj/Documents/experiment/testdata/ref.filtered";

	public static final String logPath = "/home/xj/Documents/experiment/result/130427/skeleton-decode.log";
	public static final String logOutPath = "/home/xj/Documents/experiment/result/130427/skeleton-decode-failure.log";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NiuCorpusKickFailureSents().kick();
	}
	
	public void kick() {
		BufferedReader brLog = null;
		BufferedReader br = null;
		BufferedReader brT = null;
		BufferedReader brR = null;
		BufferedWriter bw = null;
		BufferedWriter bwT = null;
		BufferedWriter bwR = null;
		BufferedWriter bwLog = null;
		try {
			brLog = new BufferedReader(new FileReader(new File(logPath)));
			brT = new BufferedReader(new FileReader(new File(testTreeFilePath)));
			brR = new BufferedReader(new FileReader(new File(refFilePath)));
			br = new BufferedReader(new FileReader(new File(testFilePath)));
			bw = new BufferedWriter(new FileWriter(new File(testOutPath)));
			bwT = new BufferedWriter(new FileWriter(new File(testTreeOutPath)));
			bwR = new BufferedWriter(new FileWriter(new File(refOutPath)));
			bwLog = new BufferedWriter(new FileWriter(new File(logOutPath)));
			int failureBefore = 0;    // last line number of failure sentence
			int lineNo = 0;
			String log = null;
			while ((log = brLog.readLine()) != null) {
				++ lineNo;
				int failureNow = Integer.parseInt(log.split(":")[3].replaceAll("[^\\d]+", ""));  // sentence 1 thread 0 [elapsed: 0.11s, speed: 9.22sent/s, failure: 0] 
				if (failureNow == failureBefore) {  // this line is failure
					bw.write(br.readLine() + "\n");
					bwT.write(brT.readLine() + "\n");
					for (int i = 0; i < 3; ++i) {
						bwR.write(brR.readLine() + "\n");
					}
				} else {
					bwLog.write(lineNo + "\n");
					br.readLine();
					brT.readLine();
					for (int i = 0; i < 3; ++i) 
						brR.readLine();
					failureBefore = failureNow;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				bwT.close();
				bwR.close();
				bwLog.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
