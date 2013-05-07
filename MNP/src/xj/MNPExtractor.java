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
public class MNPExtractor {

	public static String inPath = "/home/xj/Documents/experiment/testdata/test.tree";
	public static String mnpPath = "/home/xj/Documents/experiment/result/130427/MNPExracted";
	public static String outPath = "/home/xj/Documents/experiment/result/130427/skeleton";

	public static int wordCt = 0;
	public static int wordCtAfterExtract = 0;
	public static int mnpCt = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		BufferedWriter extractOut = null;
		try {
			reader = new BufferedReader(new FileReader(new File(inPath)));
			writer = new BufferedWriter(new FileWriter(new File(outPath)));
			extractOut = new BufferedWriter(new FileWriter(new File(mnpPath)));
			String sentPair;
			int sentId = 1;
			while ((sentPair = reader.readLine()) != null) {
				//
//				 System.err.println(tmpStr);
				//
//				extractOut.write(sentId + "\n");
				++ sentId;
				String[] sents = sentPair.split(" \\p{Punct}{4}  \\p{Punct}{4} ");
				if (sents.length < 2) {
					System.err.println("Wrong sentence pattern.");
				}
				StringBuffer tree = new StringBuffer(sents[1]);
				StringBuffer sent = new StringBuffer(sents[0]);
				int npId = 1;
				int lastEnd = 0;
				while (true) {
					int mnpStart = tree.indexOf("(NP", lastEnd);
					
					if (mnpStart < 0) { /* 没有NP 或已经全部被替换完, 统计替换前后的单词数 */
						wordCt += sents[0].split("\\s+").length;
						wordCtAfterExtract += sent.toString().split("\\s+").length;
						//
						System.err.println((sentId-1) + " -- " + sents[0].split("\\s+").length + " -- " + sent.toString().split("\\s+").length);
						//
						break;
					}
					int mnpEnd = 0;
					int hiera = 0;
					boolean isWord = true;
					for (int i = mnpStart; i < tree.length(); ++i) {
						if (tree.charAt(i) == '(') {
							++hiera;
							if (hiera > 2)              //如果hiera在括号匹配的过程中出现大于1的情况则判定为不是单个词
								isWord = false;
						} else if (tree.charAt(i) == ')') {
							--hiera;
						}
						if (hiera <= 0) {
							mnpEnd = i;
							break;
						}
					}
					
					/* 处理提取出的mnp */
					if (!isWord) {
						String mnpPhrase = tree.substring(mnpStart, mnpEnd + 1)
								.replaceAll("([a-zA-Z]|-|[()])+", "")
								.replaceAll("\\s+", " ").trim();
						extractOut.write(mnpPhrase + " ||||  |||| ( " + tree.substring(mnpStart, mnpEnd + 1) + ")\n");
						++ mnpCt;

						tree.replace(mnpStart, mnpEnd + 1, "(NP MNP" + npId + ")");
						int mnpStartInSent = sent.indexOf(mnpPhrase);
						if (mnpStartInSent >= 0)  {
							sent.replace(mnpStartInSent, mnpStartInSent + mnpPhrase.length(), "MNP" + npId);
						}
						++npId;
						lastEnd = mnpStart + 4;  // MNPx 占4个字符

					} else {
						lastEnd = mnpEnd;
					}
					
					
				}
				extractOut.write("\n");
				//
//				System.out.println(sent + " ||||  |||| " + tree);
				//
				writer.write(sent + " ||||  |||| " + tree + "\n");
			}
			
			extractOut.write("\n\nTotally " + mnpCt + " phrases\n");
			extractOut.write("\nBefore extraction, " + (wordCt/1000.0) + " words per sentence.");
			extractOut.write("\nAfter extraction, " + (wordCtAfterExtract/1000.0) + " words per sentence.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				extractOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}