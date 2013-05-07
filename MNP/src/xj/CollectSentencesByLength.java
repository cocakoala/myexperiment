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
public class CollectSentencesByLength {

	public static final String inPath = "/home/xj/Documents/myexperiment/testdata/test938";
	public static final String s0_20 = "/home/xj/Documents/myexperiment/050713/sents0_20";
	public static final String s20_40 = "/home/xj/Documents/myexperiment/050713/sents20_40";
	public static final String s40_ = "/home/xj/Documents/myexperiment/050713/sents40_";
	public static final String log = "/home/xj/Documents/myexperiment/050713/collectSents.log";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CollectSentencesByLength().collect();
	}
	
	public void collect() {
		BufferedReader br = null;
		BufferedWriter bw0 = null;
		BufferedWriter bw1 = null;
		BufferedWriter bw2 = null;
		BufferedWriter bwlog = null;
		try {
			br = new BufferedReader(new FileReader(new File(inPath)));
			bw0 = new BufferedWriter(new FileWriter(new File(s0_20)));
			bw1 = new BufferedWriter(new FileWriter(new File(s20_40)));
			bw2 = new BufferedWriter(new FileWriter(new File(s40_)));
			bwlog = new BufferedWriter(new FileWriter(new File(log)));
			
			String in = null;
			while ((in = br.readLine()) != null) {
				int wordCount = in.split("\\s+").length;
				bwlog.write(in + " -- " + wordCount + "\n");
				if (wordCount < 20) {
					bw0.write(in + "\n");
				} else if (wordCount >= 40) {
					bw2.write(in + "\n");
				} else {
					bw1.write(in + "\n");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw0.close();
				bw1.close();
				bw2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
