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
public class FormatMNPOut {
	
	public static final String mnpCh = "/home/xj/Documents/experiment/result/130428/MNPExtracted.withBlankLine";
	public static final String mnpToFormat = "/home/xj/Documents/experiment/result/130428/Our/MNPExtracted.t2s.out";
	public static final String output = "/home/xj/Documents/experiment/result/130428/Our/MNPExtracted.t2s.out.formatted";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new FormatMNPOut().format();
	}
	
	public void format() {
		BufferedReader brc = null;
		BufferedReader brtf = null;
		BufferedWriter bw = null;
		try {
			brc = new BufferedReader(new FileReader(new File(mnpCh)));
			brtf = new BufferedReader(new FileReader(new File(mnpToFormat)));
			bw = new BufferedWriter(new FileWriter(new File(output)));
			String inc = null, intf = null;
			while ((inc = brc.readLine()) != null) {
				if (inc.length() < 1) {
					bw.write("\n");
				} else {
					bw.write(brtf.readLine() + "\n");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
