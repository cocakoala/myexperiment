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
import java.util.ArrayList;
import java.util.List;

/**
 * @author xj
 *
 */
public class NiuCorpusKickWrongSents {
	
	public static final String testFilePath = "/home/xj/Documents/experiment/testdata/test.txt";
	public static final String testTreeFilePath = "/home/xj/Documents/experiment/testdata/test.tree.txt";
	public static final String testOutPath = "/home/xj/Documents/experiment/testdata/test.filtered.txt";
	public static final String testTreeOutPath = "/home/xj/Documents/experiment/testdata/test.tree.filtered.txt";
	public static final String refFilePath = "/home/xj/Documents/experiment/testdata/ref.txt";
	public static final String refOutPath = "/home/xj/Documents/experiment/testdata/ref.filtered.txt";
	
	public int[] wrongLines = {942, 951, 963, 976, 987, 989};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NiuCorpusKickWrongSents().solve();
	}
	
	public void solve() {
		List<Integer> wl = new ArrayList<Integer>();
		for (int i = 0; i < wrongLines.length; ++i) {
			wl.add(wrongLines[i]);
		}
		int lineNo = 0;
		BufferedReader br = null;
		BufferedReader brT = null;
		BufferedReader brR = null;
		BufferedWriter bw = null;
		BufferedWriter bwT = null;
		BufferedWriter bwR = null;
		try {
			br = new BufferedReader(new FileReader(new File(testFilePath)));
			brT = new BufferedReader(new FileReader(new File(testTreeFilePath)));
			brR = new BufferedReader(new FileReader(new File(refFilePath)));
			bw = new BufferedWriter(new FileWriter(new File(testOutPath)));
			bwT = new BufferedWriter(new FileWriter(new File(testTreeOutPath)));
			bwR = new BufferedWriter(new FileWriter(new File(refOutPath)));
			String line = null, lineT = null, lineR = null;
			while ((line = br.readLine()) != null) {
				boolean keep = true;
				lineT = brT.readLine();
				++ lineNo;
				if (isWrongLine(lineNo)) {
					keep = false; 
				} 
				if (containsWrongChar(line)) {
					wl.add(lineNo);
					keep = false;
				}
				
				if (keep) {
					bw.write(line + "\n");
					bwT.write(lineT + "\n");
				}
				for (int i = 0; i < 3; ++i) {
					lineR = brR.readLine();
					if (keep) 
						bwR.write(lineR + "\n");
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isWrongLine(int lineNo) {
		for (int i = 0; i < wrongLines.length; ++i) {
			if (wrongLines[i] == lineNo) 
				return true;
		}
		
		return false;
	}
	
	public boolean containsWrongChar(String line) {
		if (line.indexOf('(') >= 0 || line.indexOf(')') >= 0)
			return true;
		
		return false;
	}
}
