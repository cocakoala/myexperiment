package xj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KickSentsWithBrackets {

	public String sentPath = "/home/xj/Documents/experiment/052413/english.txt";
	public String treePath = "/home/xj/Documents/experiment/052413/eng.tree";
	public String sentwPath = "/home/xj/Documents/experiment/052413/english.kickbracket";
	public String treewPath = "/home/xj/Documents/experiment/052413/english.tree.kickbracket";
	public String chnSentPath = "/home/xj/Documents/experiment/052413/chinese.txt";
	public String chnSentwPath = "/home/xj/Documents/experiment/052413/chinese.kickbracket";
	public String alignPath = "/home/xj/Documents/experiment/052413/Alignment.txt";
	public String alignwPath = "/home/xj/Documents/experiment/052413/Alignment.kickbracket";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new KickSentsWithBrackets().kick();
	}
	
	public void kick() {
		BufferedReader brSent = null, brTree = null, brChnSent = null, brAlign = null;
		BufferedWriter bwSent = null, bwTree = null, bwChnSent = null, bwAlign = null;
		try {
			brSent = new BufferedReader(new FileReader(new File(sentPath)));
			brTree = new BufferedReader(new FileReader(new File(treePath)));
			brChnSent = new BufferedReader(new FileReader(new File(chnSentPath)));
			brAlign = new BufferedReader(new FileReader(new File(alignPath)));
			bwSent = new BufferedWriter(new FileWriter(new File(sentwPath)));
			bwTree = new BufferedWriter(new FileWriter(new File(treewPath)));
			bwChnSent = new BufferedWriter(new FileWriter(new File(chnSentwPath)));
			bwAlign = new BufferedWriter(new FileWriter(new File(alignwPath)));
			
			String sent = null, tree = null, chnSent = null, align = null;
			while ((sent = brSent.readLine()) != null) {
				tree = brTree.readLine();
				chnSent = brChnSent.readLine();
				align = brAlign.readLine();
				if (sent.indexOf('(') >= 0 || sent.indexOf(')') >= 0 || tree.length() < 5)
					continue;
				bwSent.write(sent + "\n");
				bwTree.write(tree + "\n");
				bwChnSent.write(chnSent + "\n");
				bwAlign.write(align + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bwSent.close();
				bwTree.close();
				bwChnSent.close();
				bwAlign.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
