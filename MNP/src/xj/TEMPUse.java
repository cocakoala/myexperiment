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
public class TEMPUse {

	public static final String inPath0 = "/home/xj/Documents/experiment/130624/mnp40_.withBlankLine";
	public static final String inPath1 = "/home/xj/Documents/experiment/130624/mnp_trans40_";
	public static final String outPath = "/home/xj/Documents/experiment/130624/mnp_trans40_.withBlankline";
	
	/**
	 * add blank lines to the mnpTranslation
	 */
	public void formatMnpTranslation() {
		BufferedReader br0 = null;
		BufferedReader br1 = null;
		BufferedWriter bw = null;
		
		try {
			br0 = new BufferedReader(new FileReader(new File(inPath0)));
			br1 = new BufferedReader(new FileReader(new File(inPath1)));
			bw = new BufferedWriter(new FileWriter(new File(outPath)));
			String tmp = null;
			while ( ( tmp = br0.readLine() ) != null ) {
				if ( tmp.length() > 0 )
					bw.write( br1.readLine() + "\n" );
				else 
					bw.write( "\n" );
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TEMPUse().formatMnpTranslation();
	}

}
