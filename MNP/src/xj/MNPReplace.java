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
import java.util.regex.Matcher;

/**
 * @author xj
 *
 */
public class MNPReplace {

	public static final String mnpPath = "/home/xj/Documents/experiment/130624/mnp_trans40_.withBlankline";
	public static final String skeletonPath = "/home/xj/Documents/experiment/130624/skeleton_trans40_";
	public static final String outPath = "/home/xj/Documents/experiment/130624/our_trans40_";
	
	public void replace() {
		BufferedReader brmnp = null;
		BufferedReader brske = null;
		BufferedWriter bw = null;
		try {
			brmnp = new BufferedReader(new FileReader(new File(mnpPath)));
			brske = new BufferedReader(new FileReader(new File(skeletonPath)));
			bw = new BufferedWriter(new FileWriter(new File(outPath)));
			String skeleton;
			while ((skeleton = brske.readLine()) != null) {
				for (int i = 1; ; i++) {
					String mnp = brmnp.readLine();
					if (mnp == null || mnp.length() == 0)
						break;
					if (i > 0) {
//						mnp.replaceAll("\\$", "\\\\\\$");
						mnp = Matcher.quoteReplacement(mnp);
						System.out.println("MNP" + i + " ## " + mnp);
						skeleton = skeleton.replaceAll("MNP" + i, mnp);
					}
				}
				bw.write(skeleton + "\n");
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MNPReplace().replace();
	}

}
